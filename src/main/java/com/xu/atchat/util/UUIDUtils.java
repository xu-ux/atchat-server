package com.xu.atchat.util;

import java.util.UUID;

/**
 * @author: xucl
 * @createDate: 2020/2/19
 * @description: uuid生成id工具类
 */
public class UUIDUtils {

    public static String getId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static long getLongId(){
        return Math.abs(UUID.randomUUID().getLeastSignificantBits());
    }
}
