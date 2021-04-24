package com.xu.atchat.service.http.exception;

/**
 * @Author: xucl
 * @Date: 2020/8/6
 * @Description: <p></p>
 */
public class HttpException extends RuntimeException {

    public HttpException() {
        super();
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    protected HttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * 参数异常
     */
    public static class IllegalArgument extends HttpException{

        public IllegalArgument() {
        }

        public IllegalArgument(String message, Throwable cause) {
            super(message, cause);
        }

        public IllegalArgument(String message) {
            super(message);
        }

        public IllegalArgument(Throwable cause) {
            super(cause);
        }
    }

    /**
     * 操作失败
     */
    public static class OperationFailure extends HttpException{

        public OperationFailure() {
        }

        public OperationFailure(String message, Throwable cause) {
            super(message, cause);
        }

        public OperationFailure(String message) {
            super(message);
        }

        public OperationFailure(Throwable cause) {
            super(cause);
        }
    }

    /**
     * 数据不存在
     */
    public static class NotFundData extends HttpException{
        public NotFundData() {
            super();
        }

        public NotFundData(String message, Throwable cause) {
            super(message, cause);
        }

        public NotFundData(String message) {
            super(message);
        }

        public NotFundData(Throwable cause) {
            super(cause);
        }
    }
}
