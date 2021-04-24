package com.xu.atchat.netty.strategy;

import com.alibaba.fastjson.JSONObject;
import com.xu.atchat.constant.action.ActionMessageEnum;
import com.xu.atchat.model.dto.chat.ActionStrategyMessage;
import com.xu.atchat.model.dto.chat.ChatContent;
import com.xu.atchat.model.dto.chat.ChatMessage;
import com.xu.atchat.service.IAsyncChatMsgService;
import com.xu.atchat.util.SpringUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/7/4 13:18
 * @description 执行消息签收
 */
@Slf4j
public class SignActionStrategy implements ActionStrategy  {

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
        try {
            IAsyncChatMsgService asyncChatMsgService = SpringUtils.getBean(IAsyncChatMsgService.class);
            ChatMessage chatMessage = chatContent.getChatMessage();
            log.warn("签收数据，{}",JSONObject.toJSONString(chatMessage));
            asyncChatMsgService.updateMessageSign(chatMessage.getSignIds());
        } catch (Exception e) {
            log.error("批量处理消息签收失败",e);
            return ActionMessageEnum.EXCEPTION.getMsg();
        }
        return ActionMessageEnum.SUCCESS.getMsg();
    }
}
