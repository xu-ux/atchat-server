package com.xu.atchat.constant.action;

import com.xu.atchat.model.dto.chat.ActionStrategyMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/29 14:59
 * @description 执行策略的消息传递枚举
 */
@AllArgsConstructor
@Getter
public enum ActionMessageEnum {

    SUCCESS(550001,"执行Action成功"){
        @Override
        public ActionStrategyMessage getMsg() {
            return new ActionStrategyMessage(true,SUCCESS.getErrCode(),null,null);
        }
    },

    EXCEPTION(550002,"执行Action异常"){
        @Override
        public ActionStrategyMessage getMsg() {
            return new ActionStrategyMessage(false,EXCEPTION.getErrCode(),null,null);
        }
    },

    ;
    /**
     * 错误码
     */
    private int errCode;
    /**
     * 描述
     */
    private String desc;

    public abstract ActionStrategyMessage getMsg();


}
