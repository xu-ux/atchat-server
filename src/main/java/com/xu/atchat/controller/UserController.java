package com.xu.atchat.controller;


import com.xu.atchat.advance.enums.result.OperationFailureResultEnum;
import com.xu.atchat.advance.response.BaseResponse;
import com.xu.atchat.advance.response.CommonResponse;
import com.xu.atchat.advance.response.ErrorResponse;
import com.xu.atchat.constant.ErrorCode;
import com.xu.atchat.constant.FriendSearchStatus;
import com.xu.atchat.model.domain.User;
import com.xu.atchat.model.dto.UserBaseDTO;
import com.xu.atchat.model.vo.*;
import com.xu.atchat.service.IUserService;
import com.xu.atchat.service.fastdfs.FastDFSClient;
import com.xu.atchat.util.FileUtils;
import com.xu.atchat.util.MD5Utils;
import com.xu.atchat.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Slf4j
@RestController
@RequestMapping("/atchat/user")
public class UserController {

    @Autowired
    private IUserService userService;
    
    @Autowired
    private FastDFSClient fastDFSClient;

    @Value("${fdfs.tracker-list[0]}")
    private String tracker;

    /**
     * <p>校验登录</p>
     *
     * @param userLoginVO 登录信息
     * @return
     */
    @PostMapping("login")
    public Object login(@RequestBody UserLoginVO userLoginVO){
        userLoginVO.setPassword(MD5Utils.getMD5Str(userLoginVO.getPassword()));
        UserBaseDTO userBaseDTO = userService.findOneByUsernameAndPassword(userLoginVO);
        return new CommonResponse<UserBaseDTO>(userBaseDTO);
    }


    /**
     * <p>注册用户</p>
     *
     * @param userRegisterVO 注册信息
     * @return
     */
    @PostMapping("register")
    public Object register(@RequestBody @Valid UserRegisterVO userRegisterVO){
        userRegisterVO.setPassword(MD5Utils.getMD5Str(userRegisterVO.getPassword()));
        userService.saveUserRegister(userRegisterVO);
        return new BaseResponse();
    }

    /**
     * <p>验证用户名是否存在</p>
     *
     * @param userVerifyVO
     * @return
     */
    @PostMapping("verify/username")
    public Object verifyUsernameIsExist(@RequestBody @Valid UserVerifyVO userVerifyVO){
        boolean isExist = userService.findUsernameIsExist(userVerifyVO.getUsername());
        return new CommonResponse<Boolean>(isExist);
    }

    /**
     * <p>查询用户</p>
     *
     * @param id 主键
     * @return
     */
    @GetMapping("findOne/{id}")
    public Object findOne(@PathVariable String id){
        User user = userService.getById(id);
        OperationFailureResultEnum.USER_NOT_FOUND.assertNotNull(user,id);
        return new CommonResponse<UserBaseDTO>(new UserBaseDTO(user));
    }

    /**
     * <p>上传头像</p>
     *
     * @param userPortraitVO
     * @return
     */
    @PostMapping("upload/face/data")
    @Transactional
    public Object uploadFaceData(@RequestBody @Valid UserPortraitVO userPortraitVO) {
        // 获取前端传过来的base64字符串, 然后转换为文件对象再上传
        synchronized (this) {
            //将字符串转为字节数组
            String portraitData = userPortraitVO.getPortraitData();
            byte[] bytes = FileUtils.base64DataToByte(portraitData);
            StringBuilder fileName = new StringBuilder(userPortraitVO.getUsername());
            fileName.append("=").append(UUIDUtils.getId()).append(".png");
            //通过字节数组创建file
            MultipartFile portraitFile = new MockMultipartFile(fileName.toString(), "png", "image/png", bytes);

            try {
                //上传文件
                String url = fastDFSClient.uploadBase64(portraitFile);
                //日志
                log.info("文件上传至FastDFS 成功，result url:[{}]", url);

                //存储路径 "dhawuidhwaiuh3u89u98432.png" -> "dhawuidhwaiuh3u89u98432_80x80.png"
                String arr[] = url.split("\\.");
                String thumpUrl = arr[0] + "_80x80." + arr[1];

                //更新用户
                User user = new User();
                user.setPortraitRaw(url);
                user.setPortraitThumb(thumpUrl);
                user.setId(userPortraitVO.getUserId());
                boolean b = userService.updateById(user);
                //断言如果修改用户成功true，则不抛出异常
                OperationFailureResultEnum.UPDATE_DATA_FAIL.assertIsTrue(b, User.class.getName());

                //回调
                User userResult = userService.getById(userPortraitVO.getUserId());
                return new CommonResponse(userResult);
            } catch (Exception e) {
                log.error("文件上传至FastDFS 失败，tracker:[{}] username:[{}]", tracker, userPortraitVO.getUsername(),e);
                return new ErrorResponse(7003, "文件上传至FastDFS 失败");
            }
        }
    }


    /**
     * <p>修改昵称</p>
     *
     * @param userUpdateInfoVO
     * @return
     */
    @PostMapping("update/nickname")
    @Transactional
    public Object updateNickname(@RequestBody UserUpdateInfoVO userUpdateInfoVO){
        User user = new User();
        user.setId(userUpdateInfoVO.getUserId());
        user.setNickname(userUpdateInfoVO.getNickname());

        /*ArgumentResultEnum.VALID_ERROR.assertNotEmpty();*/

        userService.updateById(user);
        User resultUser = userService.getById(userUpdateInfoVO.getUserId());
        return new CommonResponse<UserBaseDTO>(new UserBaseDTO(resultUser));
    }

    /**
     * <p>搜索好友</p>
     * @param userFriendVO
     * @return
     */
    @PostMapping("search/friend/username")
    public Object searchFriendByUsername(@RequestBody UserFriendVO  userFriendVO ){
        FriendSearchStatus friendSearchStatus = userService.searchFriends(userFriendVO.getUserId(), userFriendVO.getUsername());
        if (friendSearchStatus != null){
            return new ErrorResponse(ErrorCode.FriendSearchError.getCode(),friendSearchStatus.getMsg());
        }else {
            User user = userService.findOneByUsername(userFriendVO.getUsername());
            return new CommonResponse<SearchFriendVO>(new SearchFriendVO(user));
        }
    }

    //测试


}
