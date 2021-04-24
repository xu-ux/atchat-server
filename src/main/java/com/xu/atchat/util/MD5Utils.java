package com.xu.atchat.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/4/18 14:08
 * @description
 */
public class MD5Utils {

    /**
     * 对字符串进行md5加密
     *
     * @param strValue
     */
    public static String getMD5Str(String strValue){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
            return newstr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
