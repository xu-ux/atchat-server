package com.xu.atchat.netty.strategy;

import com.alibaba.fastjson.JSON;
import com.xu.atchat.cache.ChannelCache;
import com.xu.atchat.constant.action.ActionMessageEnum;
import com.xu.atchat.model.dto.chat.ActionStrategyMessage;
import com.xu.atchat.model.dto.chat.ChatContent;
import com.xu.atchat.model.dto.chat.ChatMessage;
import com.xu.atchat.service.IAsyncChatMsgService;
import com.xu.atchat.util.SpringUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/29 16:47
 * @description 文本消息处理
 */
@Slf4j
public class TextActionStrategy implements ActionStrategy {

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
        IAsyncChatMsgService asyncChatMsgService = SpringUtils.getBean(IAsyncChatMsgService.class);
        ChannelCache channelCache = SpringUtils.getBean(ChannelCache.class);
        try {
            asyncChatMsgService.saveChatMsg(chatContent);
            ChatMessage chatMessage = chatContent.getChatMessage();
            // 发送信息
            Channel acceptChannel = channelCache.getCache(chatMessage.getAcceptUserId());
            if (null != acceptChannel){
                log.info(JSON.toJSONStringWithDateFormat(chatContent, "yyyy-MM-dd HH:mm:ss"));
                acceptChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONStringWithDateFormat(chatContent,
                        "yyyy-MM-dd HH:mm:ss")));
            }else {
                // 第三方推送(如友盟 个推)
            }
        } catch (Exception e) {
            log.error("文本消息中转处理失败",e);
            return ActionMessageEnum.EXCEPTION.getMsg();
        }
        return ActionMessageEnum.SUCCESS.getMsg();
    }
}
