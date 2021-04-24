package com.xu.atchat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.atchat.model.domain.Friends;
import com.xu.atchat.model.dto.MyFriendDTO;

import java.util.List;

/**
 * <p>
 * 好友关联 服务类
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
public interface IFriendsService extends IService<Friends> {

    /**
     * 添加好友
     * @param userId
     * @param friendUserId
     * @return
     */
    boolean addFriend(String userId,String friendUserId);

    /**
     * 查询我的好友列表
     * @param userId
     * @return
     */
    List<MyFriendDTO> listMyFriend(String userId);

}
