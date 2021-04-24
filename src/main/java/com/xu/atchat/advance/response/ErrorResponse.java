package com.xu.atchat.advance.response;

/**
 * <p>错误返回结果</p>
 *
 */
public class ErrorResponse extends BaseResponse {

    public ErrorResponse() {
    }

    public ErrorResponse(int code, String message) {
        super(code, message);
    }
}
