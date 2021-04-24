package com.xu.atchat.service.push;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/29 17:01
 * @description
 */
public interface IDingtalkService {

    /**
     * 根据堆栈信息发送文本消息
     * @param throwable
     * @return
     */
    Boolean sendTextMessageByThrowable(Throwable throwable);

    /**
     * 根据堆栈信息发送markdown信息
     * @param throwable
     * @return
     */
    Boolean sendMarkdownByThrowable(Throwable throwable);

    /**
     * 发送文本消息
     * @param message
     * @return
     */
    Boolean sendTextMessage(String message);
}
