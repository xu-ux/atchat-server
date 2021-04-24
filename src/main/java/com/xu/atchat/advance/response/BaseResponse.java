package com.xu.atchat.advance.response;


import com.xu.atchat.advance.enums.IResponseEnum;
import com.xu.atchat.advance.enums.result.CommonResultEnum;
import lombok.Data;

/**
 * <p>基础返回结果</p>
 *
 */
@Data
public class BaseResponse {
    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回消息
     */
    protected String message;

    public BaseResponse() {
        // 默认创建成功的回应
        this(CommonResultEnum.SUCCESS);
    }

    public BaseResponse(IResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
