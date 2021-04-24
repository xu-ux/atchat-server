package com.xu.atchat.netty.strategy;

import com.xu.atchat.cache.ChannelCache;
import com.xu.atchat.constant.action.ActionMessageEnum;
import com.xu.atchat.model.dto.chat.ActionStrategyMessage;
import com.xu.atchat.model.dto.chat.ChatContent;
import com.xu.atchat.model.dto.chat.ChatMessage;
import com.xu.atchat.util.SpringUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/7/4 13:14
 * @description 第一次open时关联用户id和channelId
 */
@Slf4j
public class ConnectActionStrategy implements ActionStrategy {

    /**
     * 消息动作策略执行
     *
     * @param chatContent    接收消息
     * @param currentChannel 当前连接对象缓存池
     * @param objects        其他业务对象
     * @return
     */
    @Override
    public ActionStrategyMessage doOperation(ChatContent chatContent, Channel currentChannel, Object... objects) {
        ChatMessage chatMessage = chatContent.getChatMessage();
        ChannelCache channelCache = SpringUtils.getBean(ChannelCache.class);
        try {

            String sendUserId = chatMessage.getSendUserId();
            if (StringUtils.isNoneBlank(sendUserId)){
                channelCache.addCache(sendUserId,currentChannel);
                log.info("用户{}第一次open建立连接成功!",sendUserId);
            }else {
                log.warn("第一次open时 未获得sendUserId,请检查客户端请求");
            }
        } catch (Exception e) {
            log.error("第一次open时关联用户id和channelId失败",e);
            return ActionMessageEnum.EXCEPTION.getMsg();
        }
        return ActionMessageEnum.SUCCESS.getMsg();
    }
}
