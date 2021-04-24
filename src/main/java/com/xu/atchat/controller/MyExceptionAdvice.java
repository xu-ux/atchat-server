package com.xu.atchat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/30 14:34
 * @description
 */
@Slf4j
@ControllerAdvice
public class MyExceptionAdvice {

/*    @ExceptionHandler(value = Exception.class)

    @ResponseBody
    public Object defaultException(HttpServletRequest request, Exception e){
        log.error(e.getMessage(), e);
        return e;
    }*/
}
