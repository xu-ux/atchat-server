package com.xu.atchat.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.atchat.advance.enums.result.OperationFailureResultEnum;
import com.xu.atchat.cache.ChannelCache;
import com.xu.atchat.constant.action.ActionTypeEnum;
import com.xu.atchat.model.domain.Friends;
import com.xu.atchat.mapper.FriendsMapper;
import com.xu.atchat.model.dto.MyFriendDTO;
import com.xu.atchat.model.dto.chat.ChatContent;
import com.xu.atchat.service.IFriendsService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 好友关联 服务实现类
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Service
@Transactional
public class FriendsServiceImpl extends ServiceImpl<FriendsMapper, Friends> implements IFriendsService, IService<Friends> {

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    private ChannelCache channelCache;
    /**
     * 添加好友
     *
     * @param userId 好友申请发送方
     * @param friendUserId 接收方
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addFriend(String userId, String friendUserId) {
        if (!searchFriendsIsExist(userId,friendUserId)){
            //不存在时才添加
            Friends friends1 = new Friends();
            friends1.setUserId(userId);
            friends1.setFriendsUserId(friendUserId);
            friends1.setAddTime(LocalDateTime.now());
            //int insert = friendsMapper.insert(friends);
            Friends friends2 = new Friends();
            friends2.setUserId(friendUserId);
            friends2.setFriendsUserId(userId);
            friends2.setAddTime(LocalDateTime.now());
            boolean b = this.saveBatch(new ArrayList<Friends>() {{
                add(friends1);
                add(friends2);
            }});
            //OperationFailureResultEnum.UPDATE_DATA_FAIL.assertEquals(insert,2,"好友");
            OperationFailureResultEnum.ADD_DATA_FAIL.assertIsTrue(b);
            //好友申请发送方 发送通讯录更新
            Channel channel = channelCache.getCache(userId);
            if (channel != null){
                ChatContent chatContent = new ChatContent();
                chatContent.setAction(ActionTypeEnum.friend_add);
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(chatContent)));
            }
        }
        //其他情况放行(双方互相发出申请，或者一方发出多次)
        return true;
    }

    /**
     * 查询我的好友列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<MyFriendDTO> listMyFriend(String userId) {
        List<MyFriendDTO> friendDTOList = friendsMapper.queryMyFriendsList(userId);
        return friendDTOList;
    }

    /**
     * 查询好友表,好友是否存在
     * @param userId
     * @param friendUserId
     * @return true 存在 false不存在
     */
    public boolean searchFriendsIsExist(String userId, String friendUserId){
        QueryWrapper<Friends> friendsQueryWrapper = new QueryWrapper<>();
        //查找好友表
        friendsQueryWrapper.and(fq ->fq.eq("user_id",userId).eq("friends_user_id",friendUserId));
        Friends friends = friendsMapper.selectOne(friendsQueryWrapper);
        if (friends == null){
            return false;
        }
        return true;
    }
}
