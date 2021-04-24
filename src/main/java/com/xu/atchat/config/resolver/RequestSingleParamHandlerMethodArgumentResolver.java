package com.xu.atchat.config.resolver;

import com.alibaba.fastjson.JSONObject;
import com.xu.atchat.annotation.RequestSingleParam;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/30 14:07
 * @description 自定义解析器
 */
public class RequestSingleParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {


    /**
     *  判断是否支持要转换的参数类型
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestSingleParam.class);
    }

    /**
     * 当支持后进行相应的转换
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestSingleParam requestSingleParam = parameter.getParameterAnnotation(RequestSingleParam.class);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        int rd;
        while ((rd = reader.read(buf)) != -1) {
            sb.append(buf, 0, rd);
        }
        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
        String value = requestSingleParam.value();
        return jsonObject.get(value);
    }

}