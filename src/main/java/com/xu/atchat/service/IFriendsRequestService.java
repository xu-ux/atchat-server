package com.xu.atchat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.atchat.model.domain.FriendsRequest;
import com.xu.atchat.model.vo.FriendRequestVO;

/**
 * <p>
 * 好友申请 服务类
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
public interface IFriendsRequestService extends IService<FriendsRequest> {

    /**
     * 发送添加好友请求
     * @param userId 发送人id
     * @param friendUsername 好友用户名
     * @return
     */
    boolean sendFiendsRequest(String userId,String friendUsername,String sendRemark);


    /**
     * 好友请求列表
     * @param userId 我的id（被添加人）
     * @return
     */
    FriendRequestVO listFriendsRequest(String userId);

    /**
     * 处理好友请求
     * @param id
     * @param type
     * @return
     */
    int processFriendsRequest(String id,Integer type);

    /**
     * 统计申请数量
     * @param userId
     * @return
     */
    Integer countFriendsRequestByApply(String userId);

}
