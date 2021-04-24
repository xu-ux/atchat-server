package com.xu.atchat.model.dto.chat;

import com.xu.atchat.constant.action.ActionTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 17:38
 * @description 通信传输基本数据
 */
@Getter
@Setter
public class ChatContent implements Serializable {

    private static final long serialVersionUID = -3923142831024018635L;
    /**
     * 执行动作
     */
    private ActionTypeEnum action;
    /**
     * 消息容器
     */
    private ChatMessage chatMessage;
    /**
     * 扩展属性
     */
    private String extend;


}
