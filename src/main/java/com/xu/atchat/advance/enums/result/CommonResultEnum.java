package com.xu.atchat.advance.enums.result;


import com.xu.atchat.advance.exception.BaseException;
import com.xu.atchat.advance.assertion.CommonExceptionAssert;
import com.xu.atchat.advance.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>通用返回结果</p>
 *
 */
@Getter
@AllArgsConstructor
public enum CommonResultEnum implements CommonExceptionAssert {
    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),
    /**
     * 服务器繁忙，请稍后重试
     */
    SERVER_BUSY(9998, "服务器繁忙"),
    /**
     * 服务器异常，无法识别的异常，尽可能对通过判断减少未定义异常抛出
     */
    SERVER_ERROR(9999, "网络异常"),

    /**
     * 5***，一般对应于{@link }，系统封装的工具出现异常
     */

    // Time
    DATE_NOT_NULL(5001, "日期不能为空"),
    DATETIME_NOT_NULL(5001, "时间不能为空"),
    TIME_NOT_NULL(5001, "时间不能为空"),
    DATE_PATTERN_MISMATCH(5002, "日期[{0}]与格式[{1}]不匹配，无法解析"),
    PATTERN_NOT_NULL(5003, "日期格式不能为空"),
    PATTERN_INVALID(5003, "日期格式[{0}]无法识别"),

    USER_LOGIN_FAIL(400,"用户[{0}]登录失败，请检查用户名和密码")
    ;

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;

    /**
     * 校验返回结果是否成功
     * @param response 远程调用的响应
     */
    public static void assertSuccess(BaseResponse response) {
        SERVER_ERROR.assertNotNull(response);
        int code = response.getCode();
        if (CommonResultEnum.SUCCESS.getCode() != code) {
            String msg = response.getMessage();
            throw new BaseException(code, msg);
        }
    }
}
