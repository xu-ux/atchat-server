package com.xu.atchat.controller;


import com.xu.atchat.advance.enums.result.ArgumentResultEnum;
import com.xu.atchat.advance.enums.result.OperationFailureResultEnum;
import com.xu.atchat.advance.response.BaseResponse;
import com.xu.atchat.advance.response.CommonResponse;
import com.xu.atchat.advance.response.ErrorResponse;
import com.xu.atchat.constant.ErrorCode;
import com.xu.atchat.constant.FriendSearchStatus;
import com.xu.atchat.constant.FriendsRequestStatus;
import com.xu.atchat.model.domain.FriendsRequest;
import com.xu.atchat.model.domain.User;
import com.xu.atchat.model.vo.FriendRequestVO;
import com.xu.atchat.model.vo.SearchFriendVO;
import com.xu.atchat.model.vo.UserFriendVO;
import com.xu.atchat.service.IFriendsRequestService;
import com.xu.atchat.service.IFriendsService;
import com.xu.atchat.service.IUserService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

/**
 * <p>
 * 好友申请 前端控制器
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@RestController
@RequestMapping("/atchat/friends-request")
public class FriendsRequestController {


    @Autowired
    private IUserService userService;

    @Autowired
    private IFriendsRequestService friendsRequestService;

    /**
     * <p>发出添加好友请求</p>
     * @param userFriendVO
     * @return
     */
    @PostMapping("send/add")
    public Object sendFriendRequest(@RequestBody UserFriendVO userFriendVO ){
        FriendSearchStatus friendSearchStatus = userService.searchFriends(userFriendVO.getUserId(), userFriendVO.getUsername());
        if (friendSearchStatus != null){
            return new ErrorResponse(ErrorCode.FriendSearchError.getCode(),friendSearchStatus.getMsg());
        }else {
            //校验通过则添加请求
            boolean b = friendsRequestService.sendFiendsRequest(userFriendVO.getUserId(), userFriendVO.getUsername(),userFriendVO.getSendRemark());
            if (b){
                return new BaseResponse();
            }else {
                return new ErrorResponse(ErrorCode.FriendAddRequestError.getCode(),ErrorCode.FriendAddRequestError.getMsg());
            }

        }
    }

    /**
     * <p>我的好友请求列表</p>
     *
     * @param myUserId 被添加人用户id
     * @return
     */
    @GetMapping("list/search/my")
    public Object listFriendsRequest(String myUserId){
        ArgumentResultEnum.PARAM_NOTNULL.assertNotEmpty(myUserId,"用户ID");
        FriendRequestVO friendRequestVO = friendsRequestService.listFriendsRequest(myUserId);
        return new CommonResponse<>(friendRequestVO);
    }

    /**
     * <p>统计申请中的数量</p>
     *
     * @param myUserId
     * @return
     */
    @GetMapping("count/apply/my")
    public Object countApplyFriendsRequest(String myUserId){
        ArgumentResultEnum.PARAM_NOTNULL.assertNotEmpty(myUserId,"用户ID");
        Integer countSunm = friendsRequestService.countFriendsRequestByApply(myUserId);
        return new CommonResponse<Integer>(countSunm);
    }

    /**
     * <p>处理请求</p>
     *
     * @param id 请求主键
     * @param type 操作类型 拒绝2 同意3
     *
     * @return
     */
    @GetMapping("process")
    public Object processFriendsRequest(@RequestParam("id") String id,@RequestParam("type")Integer type){
        ArgumentResultEnum.VALID_PARAM_ERROR.assertTwoParamEquals(type,
                FriendsRequestStatus.REJECT.getId(),FriendsRequestStatus.ACCEPT.getId(),"type");
        int i = friendsRequestService.processFriendsRequest(id, type);
        OperationFailureResultEnum.UPDATE_DATA_FAIL.assertEquals(i,1,"好友请求");
        return new BaseResponse();
    }

}
