package com.xu.atchat.netty.strategy;

import com.xu.atchat.constant.action.ActionTypeEnum;
import com.xu.atchat.model.dto.chat.ActionStrategyMessage;
import com.xu.atchat.model.dto.chat.ChatContent;
import io.netty.channel.Channel;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/7/4 13:05
 * @description 消息动作执行策略接口
 * @design 策略模式
 */
public interface ActionStrategy {

    /**
     * 消息动作策略执行
     *
     * @param chatContent 接收消息
     * @param currentChannel 当前连接对象缓存池
     * @param objects 其他业务对象
     * @return
     */
    ActionStrategyMessage doOperation(ChatContent chatContent, Channel currentChannel, Object... objects);
}
