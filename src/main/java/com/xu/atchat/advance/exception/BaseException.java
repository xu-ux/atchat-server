package com.xu.atchat.advance.exception;


import com.xu.atchat.advance.enums.IResponseEnum;
import lombok.Getter;

/**
 * @author: xucl
 * @createDate: 2019/11/11
 * @description: 项目异常处理类
 */
@Getter
public class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    protected IResponseEnum responseEnum;

    /**
     * 异常消息参数
     */
    protected Object[] args;

    public BaseException(IResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public BaseException(int code, String msg) {

        super(msg);
        this.responseEnum = new IResponseEnum() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return msg;
            }
        };
    }

    public BaseException(IResponseEnum responseEnum, Object[] args, String message) {
        super(message);
        this.responseEnum = responseEnum;
        this.args = args;
    }

    public BaseException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.responseEnum = responseEnum;
        this.args = args;
    }



    public static String convertMessage(String message,Object... objects) {
        if (message.indexOf("{}")>0){
            String s = message.replaceAll("\\{\\}", "%s");
            String format = null;
            try {
                format = String.format(s, objects);
            } catch (Exception e) {
                //logger.error("convert uniform response result message failed !",e);
                return message;
            }
            return format;
        }else {
            return message;
        }
    }


}
