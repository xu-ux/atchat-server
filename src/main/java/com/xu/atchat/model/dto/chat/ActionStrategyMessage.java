package com.xu.atchat.model.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/7/4 13:12
 * @description 执行动作后返回的消息
 */
@Setter
@Getter
@AllArgsConstructor
public class ActionStrategyMessage {

    /**
     * 是否执行成功
     */
    private boolean success = false;
    /**
     * 返回码
     */
    private Integer errCode;
    /**
     * 返回消息
     */
    private String errMsg;
    /**
     * 返回数据
     */
    private Object data;

}
