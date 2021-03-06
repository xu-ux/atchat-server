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
 * @description ???????????????????????????
 */
@Slf4j
@ControllerAdvice
//@ConditionalOnMissingBean(GlobalExceptionHandler.class)
//???Spring???web??????????????????????????????????????????????????????
@ConditionalOnWebApplication
public class GlobalExceptionHandler {

    @Autowired
    private IDingtalkService dingtalkService;

    /**
     * ????????????
     */
    private final static String ENV_PROD = "prod";

    /**
     * ????????????
     */
    @Value("${spring.profiles.active}")
    private String profile;


    @Resource
    private MessageSource messageSource;


    /**
     * ???????????????
     *
     * @param e ??????
     * @return ????????????
     */
    @ExceptionHandler(value = {
            ArgumentException.class,
            BaseException.class,
            BusinessException.class,
            ValidationException.class})
    @ResponseBody
    public ErrorResponse handleBaseException(BaseException e) {
        log.error("?????????????????????", e);
        dingtalkService.sendMarkdownByThrowable(e);
        return new ErrorResponse(e.getResponseEnum().getCode(), getMessage(e));
    }

    /**
     * Controller?????????????????????
     *
     * @param e ??????
     * @return ????????????
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
        log.error("??????????????????", e);
        dingtalkService.sendMarkdownByThrowable(e);
        int code = CommonResultEnum.SERVER_ERROR.getCode();
        try {
            ServletResultEnum servletExceptionEnum = ServletResultEnum.valueOf(e.getClass().getSimpleName());
            code = servletExceptionEnum.getCode();
        } catch (IllegalArgumentException e1) {
            log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletResultEnum.class.getName());
        }

        if (ENV_PROD.equals(profile)) {
            // ??????????????????, ????????????????????????????????????????????????, ??????404.
            code = CommonResultEnum.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonResultEnum.SERVER_ERROR);
            String message = getMessage(baseException);
            return new ErrorResponse(code, message);
        }

        return new ErrorResponse(code, e.getMessage());
    }


    /**
     * ??????????????????
     *
     * @param e ??????
     * @return ????????????
     */
    @ExceptionHandler(value ={
            BindValidationException.class,
            BindException.class
    } )
    @ResponseBody
    public ErrorResponse handleBindException(BindException e) {
        log.error("????????????????????????", e);
        dingtalkService.sendMarkdownByThrowable(e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * ????????????(Valid)??????????????????????????????????????????????????????????????????
     *
     * @param e ??????
     * @return ????????????
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleValidException(MethodArgumentNotValidException e) {
        log.error("????????????????????????", e);
        dingtalkService.sendMarkdownByThrowable(e);
        return wrapperBindingResult(e.getBindingResult());
    }


    /**
     * ???????????????
     *
     * @param e ??????
     * @return ????????????
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorResponse handleException(Exception e) {
        log.error("??????????????????", e);
        dingtalkService.sendMarkdownByThrowable(e);
        if (ENV_PROD.equals(profile)) {
            // ??????????????????, ????????????????????????????????????????????????, ???????????????????????????.
            int code = CommonResultEnum.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonResultEnum.SERVER_ERROR);
            String message = getMessage(baseException);
            return new ErrorResponse(code, message);
        }

        return new ErrorResponse(CommonResultEnum.SERVER_ERROR.getCode(), e.getMessage());
    }


    /**
     * ?????????????????????
     *
     * @param e ??????
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
     * ????????????????????????
     *
     * @param bindingResult ????????????
     * @return ????????????
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



