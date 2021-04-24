package com.xu.atchat.model.vo;

import com.xu.atchat.model.dto.MessageDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/26 22:48
 * @description
 */
@Getter
@Setter
public class MessageVO {

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
    private List<MessageDTO> messageList;
}
