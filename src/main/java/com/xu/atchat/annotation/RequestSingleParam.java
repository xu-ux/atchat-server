package com.xu.atchat.annotation;

import java.lang.annotation.*;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/30 14:06
 * @description
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestSingleParam {

    String value();

    boolean required() default true;

    String defaultValue() default "";
}
