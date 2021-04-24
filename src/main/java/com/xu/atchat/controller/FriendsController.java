package com.xu.atchat.controller;


import com.xu.atchat.advance.enums.result.ArgumentResultEnum;
import com.xu.atchat.advance.response.CommonResponse;
import com.xu.atchat.model.dto.MyFriendDTO;
import com.xu.atchat.model.vo.FriendRequestVO;
import com.xu.atchat.service.IFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 好友关联 前端控制器
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@RestController
@RequestMapping("/atchat/friends")
public class FriendsController {

    @Autowired
    private IFriendsService friendsService;

    /**
     * <p>我的好友请求列表</p>
     *
     * @param myUserId 被添加人用户id
     * @return
     */
    @GetMapping("list/my")
    public Object listFriendsRequest(String myUserId){
        ArgumentResultEnum.PARAM_NOTNULL.assertNotEmpty(myUserId,"用户ID");
        List<MyFriendDTO> result = friendsService.listMyFriend(myUserId);
        return new CommonResponse<>(result);
    }

}
