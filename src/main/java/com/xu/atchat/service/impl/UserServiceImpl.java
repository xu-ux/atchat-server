package com.xu.atchat.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.atchat.advance.enums.result.CommonResultEnum;
import com.xu.atchat.advance.enums.result.OperationFailureResultEnum;
import com.xu.atchat.advance.exception.BaseException;
import com.xu.atchat.constant.FriendSearchStatus;
import com.xu.atchat.mapper.FriendsMapper;
import com.xu.atchat.model.bo.QrcodeInfo;
import com.xu.atchat.model.domain.Friends;
import com.xu.atchat.model.domain.User;
import com.xu.atchat.mapper.UserMapper;
import com.xu.atchat.model.dto.UserBaseDTO;
import com.xu.atchat.model.vo.UserLoginVO;
import com.xu.atchat.model.vo.UserRegisterVO;
import com.xu.atchat.service.IUserService;
import com.xu.atchat.service.fastdfs.FastDFSClient;
import com.xu.atchat.util.FileUtils;
import com.xu.atchat.util.QRCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * 登录
     * @param userLoginVO
     * @return
     */
    @Override
    public UserBaseDTO findOneByUsernameAndPassword(UserLoginVO userLoginVO) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.and(uq -> uq.eq("username",userLoginVO.getUsername()).eq("password",userLoginVO.getPassword()));

        User user = userMapper.selectOne(userWrapper);
        //如果用户不存在抛异常
        CommonResultEnum.USER_LOGIN_FAIL.assertNotNull(user,userLoginVO.getUsername());
        UserBaseDTO userBaseDTO = new UserBaseDTO(user);
        return userBaseDTO;
    }

    /**
     * 注册
     *
     * @param userRegisterVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRegister(UserRegisterVO userRegisterVO) {
        User userEntity = new User();
        BeanUtils.copyProperties(userRegisterVO,userEntity);
        // 创建二维码
        String qrcodeUrl = creatQrcodeFile(userRegisterVO.getUsername());
        userEntity.setQrcode(qrcodeUrl);
        int insert = userMapper.insert(userEntity);
        // 如果插入不是1，则注册失败
        OperationFailureResultEnum.USER_SAVE_ERROR.assertEquals(insert,1,userRegisterVO.getUsername());
    }

    /**
     * 创建二维码，并上传fastDFS
     * @param username
     * @return
     */
    private String creatQrcodeFile(String username) {
        String tempFilePath = "D://atChatUser//"+username+"//qrcode.png";
        QrcodeInfo qrcodeInfo = new QrcodeInfo("atchat",null,username,null);
        String json = JSON.toJSONString(qrcodeInfo);
        qrCodeUtils.createQRCode(tempFilePath,json);
        MultipartFile multipartFile = FileUtils.fileToMultipart(tempFilePath);

        String qrCodeUrl = null;
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(multipartFile);
        } catch (IOException e) {
            log.error("上传QRCode失败,username:[{}]",username);
            throw new BaseException(500,"上传QRCode失败");
        }
        return qrCodeUrl;
    }

    /**
     * 用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public boolean findUsernameIsExist(String username) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("username",username);
        User user = userMapper.selectOne(userWrapper);
        if (null != user){
            return true;
        }
        return false;
    }

    /**
     * 查询好友用户名状态
     *
     * @param myUserId       我的id
     * @param friendUsername 查找的用户名（精确查找）
     * @return
     */
    @Override
    public FriendSearchStatus searchFriends(String myUserId, String friendUsername) {
        User user = findOneByUsername(friendUsername);
        if (user == null){
            return FriendSearchStatus.USER_NOT_EXIST;
        }
        if (myUserId.equals(user.getId())){
            return FriendSearchStatus.NOT_YOURSELF;
        }
        //对已经是好友进行校验
        QueryWrapper<Friends> friendsQueryWrapper = new QueryWrapper<>();

        friendsQueryWrapper.and(fq ->fq.eq("user_id",myUserId).eq("friends_user_id",user.getId()));
        Friends friends = friendsMapper.selectOne(friendsQueryWrapper);

        if (friends != null){
            return FriendSearchStatus.ALREADY_FRIENDS;
        }
        return null;
    }

    /**
     * 根据用户名精确搜索用户
     *
     * @param username
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User findOneByUsername(String username) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("username",username);
        User user = userMapper.selectOne(userWrapper);
        return user;
    }
}
