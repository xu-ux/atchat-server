package com.xu.atchat.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/5 12:11
 * @description base64加解密
 */
public class Base64Utils {

    final static Base64.Encoder encoder = Base64.getEncoder();
    final static Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 给字符串加密
     *
     * @param text
     * @return
     */
    public static String encode(String text) {
        return encoder.encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将加密后的字符串进行解密
     *
     * @param encodedText
     * @return
     */
    public static String decode(String encodedText) {
        return new String(decoder.decode(encodedText), StandardCharsets.UTF_8);
    }

}