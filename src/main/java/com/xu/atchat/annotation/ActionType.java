package com.xu.atchat.annotation;

import com.xu.atchat.constant.action.ActionTypeEnum;

import java.lang.annotation.*;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/6/6 14:45
 * @description 执行的动作
 */
@Documented //javadoc
@Retention(RetentionPolicy.RUNTIME) // 存活 始终不丢弃
@Target({ElementType.METHOD,ElementType.TYPE}) //作用于方法和类
@Inherited //可继承
public @interface ActionType {

    ActionTypeEnum type() default ActionTypeEnum.other;

    String value() default "";
}
