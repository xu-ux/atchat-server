package com.xu.atchat.controller.handler;

import com.xu.atchat.advance.exception.ArgumentException;
import com.xu.atchat.advance.exception.BaseException;
import com.xu.atchat.advance.enums.result.ArgumentResultEnum;
import com.xu.atchat.advance.enums.result.CommonResultEnum;
import com.xu.atchat.advance.enums.result.ServletResultEnum;
import com.xu.atchat.advance.exception.BusinessException;
import com.xu.atchat.advance.exception.ValidationException;
import com.xu.atchat.advance.response.ErrorResponse;
import com.xu.atchat.service.push.IDingtalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @author xuchenglong
 * @version 1.0
 * @date 2020/4/17 17:39
 * @description 全局异常拦截处理器
 */
@Slf4j
@ControllerAdvice
//@ConditionalOnMissingBean(GlobalExceptionHandler.class)
//当Spring为web服务时，才使注解的类生效，异常未处理
@ConditionalOnWebApplication
public class GlobalExceptionHandler {

    @Autowired
    private IDingtalkService dingtalkService;

    /**
     * 生产环境
     */
    private final static String ENV_PROD = "prod";

    /**
     * 当前环境
     */
    @Value("${spring.profiles.active}")
    private String profile;


    @Resource
    private MessageSource messageSource;


    /**
     * 自定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = {
            ArgumentException.class,
            BaseException.class,
            BusinessException.class,
            ValidationException.class})
    @ResponseBody
    public ErrorResponse handleBaseException(BaseException e) {
        log.error("自定义异常捕获", e);
        dingtalkService.sendMarkdownByThrowable(e);
        return new ErrorResponse(e.getResponseEnum().getCode(), getMessage(e));
    }

    /**
     * Controller上一层相关异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class,
            NoSuchMethodError.class,
            NoSuchMethodError.class,
            AbstractMethodError.class,
            NoClassDefFoundError.class,
    })
    @ResponseBody
    public ErrorResponse handleServletException(Exception e) {
        log.error("系统异常捕获", e);
        dingtalkService.sendMarkdownByThrowable(e);
        int code = CommonResultEnum.SERVER_ERROR.getCode();
        try {
            ServletResultEnum servletExceptionEnum = ServletResultEnum.valueOf(e.getClass().getSimpleName());
            code = servletExceptionEnum.getCode();
        } catch (IllegalArgumentException e1) {
            log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletResultEnum.class.getName());
        }

        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404.
            code = CommonResultEnum.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonResultEnum.SERVER_ERROR);
            String message = getMessage(baseException);
            return new ErrorResponse(code, message);
        }

        return new ErrorResponse(code, e.getMessage());
    }


    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value ={
            BindValidationException.class,
            BindException.class
    } )
    @ResponseBody
    public ErrorResponse handleBindException(BindException e) {
        log.error("参数绑定校验异常", e);
        dingtalkService.sendMarkdownByThrowable(e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数校验(Valid)异常，将校验失败的所有异常组合成一条错误信息
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleValidException(MethodArgumentNotValidException e) {
        log.error("参数绑定校验异常", e);
        dingtalkService.sendMarkdownByThrowable(e);
        return wrapperBindingResult(e.getBindingResult());
    }


    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorResponse handleException(Exception e) {
        log.error("全局异常捕获", e);
        dingtalkService.sendMarkdownByThrowable(e);
        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如数据库异常信息.
            int code = CommonResultEnum.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonResultEnum.SERVER_ERROR);
            String message = getMessage(baseException);
            return new ErrorResponse(code, message);
        }

        return new ErrorResponse(CommonResultEnum.SERVER_ERROR.getCode(), e.getMessage());
    }


    /**
     * 获取国际化消息
     *
     * @param e 异常
     * @return
     */
    public String getMessage(BaseException e) {
        String code = "response." + e.getResponseEnum().toString();
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(code, e.getArgs(), "", locale);
        if (message == null || message.isEmpty()) {
            return e.getMessage();
        }
        return message;
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private ErrorResponse wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();

        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());

        }

        return new ErrorResponse(ArgumentResultEnum.VALID_ERROR.getCode(), msg.substring(2));
    }
}



