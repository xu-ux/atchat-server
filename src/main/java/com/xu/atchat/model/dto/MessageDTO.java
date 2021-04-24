package com.xu.atchat.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/30 14:16
 * @description
 */
@Getter
@Setter
public class MessageDTO {

    /**
     * 消息主体内容
     */
    private String message;
    /**
     * 消息主键
     */
    private String chatMsgId;
    /**
     * 发送者id
     */
    private String sendUserId;
    /**
     * 接收者id
     */
    private String acceptUserId;
    /**
     * 消息类型
     */
    private String type;
    /**
     * 信息状态
     */
    private Integer status;
    /**
     * 发送时间
     */
    private LocalDateTime sendDate;
}
