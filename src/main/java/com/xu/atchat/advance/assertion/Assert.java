package com.xu.atchat.advance.assertion;



import com.xu.atchat.advance.exception.BaseException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;

/**
 * <p>枚举类异常断言，提供简便的方式判断条件，并在条件满足时抛出异常</p>
 * <p>错误码和错误信息定义在枚举类中，在本断言方法中，传递错误信息需要的参数</p>
 */
public interface Assert {
    /**
     * 创建异常
     * @param args
     * @return
     */
    BaseException newException(Object... args);

    /**
     * 创建异常
     * @param t
     * @param args
     * @return
     */
    BaseException newException(Throwable t, Object... args);

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertNotNull(Object obj) {
        if (obj == null) {
            throw newException();
        }
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     * @param obj 待判断对象
     * @param args message占位符对应的参数列表
     */
    default void assertNotNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(args);
        }
    }

    /**
     * <p>断言字符串<code>str</code>不为空串（长度为0）。如果字符串<code>str</code>为空串，则抛出异常
     *
     * @param str 待判断字符串
     */
    default void assertNotEmpty(String str) {
        if (null == str || "".equals(str.trim())) {
            throw newException();
        }
    }


    /*default void assertNotEmpty(String... strs){
        Optional<String> s = Arrays.asList(strs).stream().filter(String::isEmpty).collect(Col);

    }*/

    /**
     * <p>断言字符串<code>str</code>不为空串（长度为0）。如果字符串<code>str</code>为空串，则抛出异常
     * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     * @param str 待判断字符串
     * @param args message占位符对应的参数列表
     */
    default void assertNotEmpty(String str, Object... args) {
        if (str == null || "".equals(str.trim())) {
            throw newException(args);
        }
    }

    /**
     * <p>断言数组<code>arrays</code>大小不为0。如果数组<code>arrays</code>大小不为0，则抛出异常
     *
     * @param arrays 待判断数组
     */
    default void assertNotEmpty(Object[] arrays) {
        if (arrays == null || arrays.length == 0) {
            throw newException();
        }
    }

    /**
     * <p>断言数组<code>arrays</code>大小不为0。如果数组<code>arrays</code>大小不为0，则抛出异常
     * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     * @param arrays 待判断数组
     * @param args message占位符对应的参数列表
     */
    default void assertNotEmpty(Object[] arrays, Object... args) {
        if (arrays == null || arrays.length == 0) {
            throw newException(args);
        }
    }

    /**
     * <p>断言集合<code>c</code>大小不为0。如果集合<code>c</code>大小不为0，则抛出异常
     *
     * @param c 待判断数组
     */
    default void assertNotEmpty(Collection<?> c) {
        if (c ==  null || c.isEmpty()) {
            throw newException();
        }
    }

    /**
     * <p>断言集合<code>c</code>大小不为0。如果集合<code>c</code>大小不为0，则抛出异常
     *
     * @param c 待判断数组
     * @param args message占位符对应的参数列表
     */
    default void assertNotEmpty(Collection<?> c, Object... args) {
        if (c ==  null || c.isEmpty()) {
            throw newException(args);
        }
    }

    /**
     * <p>断言Map<code>map</code>大小不为0。如果Map<code>map</code>大小不为0，则抛出异常
     *
     * @param map 待判断Map
     */
    default void assertNotEmpty(Map<?, ?> map) {
        if (map ==  null || map.isEmpty()) {
            throw newException();
        }
    }

    /**
     * <p>断言Map<code>map</code>大小不为0。如果Map<code>map</code>大小不为0，则抛出异常
     *
     * @param map 待判断Map
     * @param args message占位符对应的参数列表
     */
    default void assertNotEmpty(Map<?, ?> map, Object... args) {
        if (map ==  null || map.isEmpty()) {
            throw newException(args);
        }
    }

    /**
     * <p>断言布尔值<code>expression</code>为false。如果布尔值<code>expression</code>为true，则抛出异常
     *
     * @param expression 待判断布尔变量
     */
    default void assertIsFalse(boolean expression) {
        if (expression) {
            throw newException();
        }
    }

    /**
     * <p>断言布尔值<code>expression</code>为false。如果布尔值<code>expression</code>为true，则抛出异常
     *
     * @param expression 待判断布尔变量
     * @param args message占位符对应的参数列表
     */
    default void assertIsFalse(boolean expression, Object... args) {
        if (expression) {
            throw newException(args);
        }
    }

    /**
     * <p>断言布尔值<code>expression</code>为true。如果布尔值<code>expression</code>为false，则抛出异常
     *
     * @param expression 待判断布尔变量
     */
    default void assertIsTrue(boolean expression) {
        if (!expression) {
            throw newException();
        }
    }

    /**
     * <p>断言布尔值<code>expression</code>为true。如果布尔值<code>expression</code>为false，则抛出异常
     *
     * @param expression 待判断布尔变量
     * @param args message占位符对应的参数列表
     */
    default void assertIsTrue(boolean expression, Object... args) {
        if (!expression) {
            throw newException(args);
        }
    }

    /**
     * <p>断言对象<code>obj</code>为<code>null</code>。如果对象<code>obj</code>不为<code>null</code>，则抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertIsNull(Object obj) {
        if (obj != null) {
            throw newException();
        }
    }

    /**
     * <p>断言对象<code>obj</code>为<code>null</code>。如果对象<code>obj</code>不为<code>null</code>，则抛出异常
     *
     * @param obj 待判断布尔变量
     * @param args message占位符对应的参数列表
     */
    default void assertIsNull(Object obj, Object... args) {
        if (obj != null) {
            throw newException(args);
        }
    }

    /**
     * <p>直接抛出异常
     *
     */
    default void assertFail() {
        throw newException();
    }

    /**
     * <p>直接抛出异常
     *
     * @param args message占位符对应的参数列表
     */
    default void assertFail(Object... args) {
        throw newException(args);
    }

    /**
     * <p>直接抛出异常，并包含原异常信息
     * <p>当捕获非运行时异常（非继承{@link RuntimeException}）时，并该异常进行业务描述时，
     * 必须传递原始异常，作为新异常的cause
     *
     * @param t 原始异常
     */
    default void assertFail(Throwable t) {
        throw newException(t);
    }

    /**
     * <p>直接抛出异常，并包含原异常信息
     * <p>当捕获非运行时异常（非继承{@link RuntimeException}）时，并该异常进行业务描述时，
     * 必须传递原始异常，作为新异常的cause
     *
     * @param t 原始异常
     * @param args message占位符对应的参数列表
     */
    default void assertFail(Throwable t, Object... args) {
        throw newException(t, args);
    }

    /**
     * <p>断言对象<code>o1</code>与对象<code>o2</code>相等，此处的相等指（o1.equals(o2)为true）。
     * 如果两对象不相等，则抛出异常
     *
     * @param o1 待判断对象，若<code>o1</code>为null，也当作不相等处理
     * @param o2  待判断对象
     */
    default void assertEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return;
        }
        if (o1 == null) {
            throw newException();
        }
        if (!o1.equals(o2)) {
            throw newException();
        }
    }

    /**
     * <p>断言对象<code>o1</code>与对象<code>o2</code>相等，此处的相等指（o1.equals(o2)为true）。
     * 如果两对象不相等，则抛出异常
     *
     * @param o1 待判断对象，若<code>o1</code>为null，也当作不相等处理
     * @param o2  待判断对象
     * @param args message占位符对应的参数列表
     */
    default void assertEquals(Object o1, Object o2, Object... args) {
        if (o1 == o2) {
            return;
        }
        if (o1 == null) {
            throw newException(args);
        }
        if (!o1.equals(o2)) {
            throw newException(args);
        }
    }


    /**
     * <p>断言对象<code>o1</code>与对象<code>o2</code>或<code>o3</code>相等，此处的相等指（o1.equals(o2)为true）。
     * 如果两个都不相等，则抛出异常
     *
     * @param o1 待判断对象，若<code>o1</code>为null，也当作不相等处理
     * @param o2  待判断对象
     * @param o3  待判断对象
     * @param args message占位符对应的参数列表
     */
    default void assertTwoParamEquals(Object o1, Object o2, Object o3,Object... args) {
        if (o1 == o2||o1 == o3) {
            return;
        }
        if (o1 == null) {
            throw newException(args);
        }
        if (!o1.equals(o2) && !o1.equals(o3)) {
            throw newException(args);
        }
    }

   /* *//**
     * <p></p>
     * @param collection
     *//*
    default void notEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            throw newException();
        }
    }

    *//**
     *
     * @param collection
     * @param messageSupplier
     *//*
    default void notEmpty(Collection<?> collection, Supplier<String> messageSupplier) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    default String nullSafeGet(Supplier<String> messageSupplier) {
        return messageSupplier != null ? (String)messageSupplier.get() : null;
    }*/
}
