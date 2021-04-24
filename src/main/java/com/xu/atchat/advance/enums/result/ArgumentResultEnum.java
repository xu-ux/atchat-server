package com.xu.atchat.advance.enums.result;


import com.xu.atchat.advance.assertion.CommonExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>参数校验异常返回结果</p>
 *
 */
@Getter
@AllArgsConstructor
public enum ArgumentResultEnum implements CommonExceptionAssert {

    /**
     * 绑定参数校验异常
     */
    VALID_ERROR(6000, "参数校验异常"),

    /**
     * 某个参数不能为空
     */
    PARAM_NOTNULL(6001,"{0}不能为空"),

    /**
     * 绑定参数校验异常2
     */
    VALID_PARAM_ERROR(6002, "参数[%s]校验异常"),
    ;

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;

}
