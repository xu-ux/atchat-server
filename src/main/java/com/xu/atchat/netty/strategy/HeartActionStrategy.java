package com.xu.atchat.netty.strategy;

import com.alibaba.fastjson.JSON;
import com.xu.atchat.cache.ChannelCache;
import com.xu.atchat.constant.action.ActionMessageEnum;
import com.xu.atchat.constant.action.ActionTypeEnum;
import com.xu.atchat.model.dto.chat.ActionStrategyMessage;
import com.xu.atchat.model.dto.chat.ChatContent;
import com.xu.atchat.model.dto.chat.ChatMessage;
import com.xu.atchat.util.SpringUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/7/4 13:13
 * @description 执行心跳动作
 */
@Slf4j
public class HeartActionStrategy implements ActionStrategy {


    @Override
    public ActionStrategyMessage doOperation(ChatContent chatContent, Channel currentChannel, Object... objects) {
        ChatMessage chatMessage = chatContent.getChatMessage();
        ChannelCache channelCache = SpringUtils.getBean(ChannelCache.class);
        log.info("收到心跳包，channel:{}",currentChannel.id().asShortText());
        // 发送消息
        try {
            // 发送人
            String sendUserId = chatMessage.getSendUserId();
            Channel sendChannel = channelCache.getCache(sendUserId);
            // 构建反参
            ChatContent result = new ChatContent();
            result.setAction(ActionTypeEnum.pong);
            if (sendChannel != null){
                sendChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(result)));
            }
        } catch (Exception e) {
            log.error("处理心跳包失败",e);
            return ActionMessageEnum.EXCEPTION.getMsg();
        }
        return ActionMessageEnum.SUCCESS.getMsg();
    }

}
