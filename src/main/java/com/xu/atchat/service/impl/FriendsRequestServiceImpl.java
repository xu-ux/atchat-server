package com.xu.atchat.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.atchat.cache.ChannelCache;
import com.xu.atchat.constant.action.ActionTypeEnum;
import com.xu.atchat.constant.FriendsRequestStatus;
import com.xu.atchat.model.domain.FriendsRequest;
import com.xu.atchat.mapper.FriendsRequestMapper;
import com.xu.atchat.model.domain.User;
import com.xu.atchat.model.dto.FriendRequestDTO;
import com.xu.atchat.model.dto.chat.ChatContent;
import com.xu.atchat.model.vo.FriendRequestVO;
import com.xu.atchat.service.IFriendsRequestService;
import com.xu.atchat.service.IFriendsService;
import com.xu.atchat.service.IUserService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 好友申请 服务实现类
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Service
public class FriendsRequestServiceImpl extends ServiceImpl<FriendsRequestMapper, FriendsRequest> implements IFriendsRequestService {
    
    @Autowired
    private FriendsRequestMapper friendsRequestMapper;
    
    @Autowired
    private IUserService userService;

    @Autowired
    private IFriendsService friendsService;

    @Autowired
    private ChannelCache channelCache;

    /**
     * 发送添加好友请求
     * 可以重复发送，解除好友后，还是可以重复申请的
     * @param userId         发送人id
     * @param friendUsername 好友用户名
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sendFiendsRequest(String userId, String friendUsername,String sendRemark) {
        User friendUser = userService.findOneByUsername(friendUsername);
        QueryWrapper<FriendsRequest> userWrapper = new QueryWrapper<>();
        userWrapper.and(uq -> uq.eq("send_user_id",userId).eq("accept_user_id",friendUser.getId()));
        //FriendsRequest friendsRequest = friendsRequestMapper.selectOne(userWrapper);
        //如果friendsRequest不为null,则抛出异常
        //OperationFailureResultEnum.FRIEND_ADD_REQUEST_EXIST.assertIsNull(friendsRequest);

        FriendsRequest realFriendsRequest = new FriendsRequest();
        realFriendsRequest.setStatus(FriendsRequestStatus.APPLY.getId());
        realFriendsRequest.setRequestTime(LocalDateTime.now());
        realFriendsRequest.setSendUserId(userId);
        realFriendsRequest.setAcceptUserId(friendUser.getId());
        realFriendsRequest.setSendRemark(sendRemark);

        int insert = friendsRequestMapper.insert(realFriendsRequest);
        //同步被添加方的请求列表
        Channel channel = channelCache.getCache(friendUser.getId());
        if (channel != null){
            ChatContent chatContent = new ChatContent();
            chatContent.setAction(ActionTypeEnum.friend_req);
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(chatContent)));
        }
        return insert != 1 ? false : true;
    }

    /**
     * 好友请求列表
     *
     * @param userId 我的id（被添加人）
     * @return
     */
    @Override
    public FriendRequestVO listFriendsRequest(String userId) {
        List<FriendRequestDTO> friendRequestDTOS = friendsRequestMapper.queryFriendRequestList(userId);
        //friendRequestDTOS.stream().map(f-> f.setFriendsRequestStatus(FriendsRequestStatus.getFriendsRequestStatusById(f.getStatus()))).collect(Collectors.toList());
        List<FriendRequestDTO> applyList = friendRequestDTOS.stream().filter(friendRequestDTO ->
                FriendsRequestStatus.APPLY.equals(friendRequestDTO.getFriendsRequestStatus())).collect(Collectors.toList());
        List<FriendRequestDTO> processedList = new ArrayList(CollectionUtils.subtract(friendRequestDTOS, applyList));
        FriendRequestVO vo = new FriendRequestVO();
        vo.setApplyList(applyList);
        vo.setProcessedList(processedList);

        return vo;
    }

    /**
     * 处理好友请求
     *
     * @param id
     * @param type
     * @return
     */
    @SuppressWarnings("AlibabaTransactionMustHaveRollback")
    @Override
    @Transactional
    public int processFriendsRequest(String id, Integer type) {
        //查询
        FriendsRequest friendsRequestOld = friendsRequestMapper.selectById(id);
        //修改请求状态
        FriendsRequest friendsRequest = new FriendsRequest();
        friendsRequest.setId(id);
        friendsRequest.setStatus(type);
        friendsRequest.setAcceptTime(LocalDateTime.now());
        int i = friendsRequestMapper.updateById(friendsRequest);
        //添加好友
        friendsService.addFriend(friendsRequestOld.getSendUserId(),friendsRequestOld.getAcceptUserId());
        return i;
    }

    /**
     * 统计申请数量
     *
     * @param userId
     * @return
     */
    @Override
    public Integer countFriendsRequestByApply(String userId) {
        /*List<FriendsRequest> friendsRequests = friendsRequestMapper.selectByMap(new HashMap<String, Object>() {{
            put("accept_user_id", userId);
            put("status", FriendsRequestStatus.APPLY.getId());
        }});*/
        QueryWrapper<FriendsRequest> requestQueryWrapper = new QueryWrapper<>();
        requestQueryWrapper.and(rq -> rq.eq("accept_user_id", userId).eq("status", FriendsRequestStatus.APPLY.getId()));
        int count = this.count(requestQueryWrapper);
        return count;
    }
}
