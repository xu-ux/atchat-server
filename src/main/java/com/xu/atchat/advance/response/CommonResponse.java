package com.xu.atchat.advance.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>通用返回结果</p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonResponse<T> extends BaseResponse {
    /**
     * 数据列表
     */
    protected T data;

    public CommonResponse() {
        super();
    }

    public CommonResponse(T data) {
        super();
        this.data = data;
    }
}
