package com.xu.atchat.util.encrypt;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 19:10
 * @description DES对称加密
 */
public class DESEncrypet {


    private static Cipher cipher;

    private static Key convertSecretKey;

    private static final String SECRET = "1148049068@qq.com&#$%77*(65~!@%";

    static {
        try {
            ////Key的生成器
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            //指定keySize
            keyGenerator.init(56,new SecureRandom(SECRET.getBytes("utf-8")));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            //实例化DESKey秘钥的相关内容
            DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
            //实例一个秘钥工厂，指定加密方式
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            convertSecretKey = factory.generateSecret(desKeySpec);
            //通过Cipher这个类进行加解密相关操作
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密
     *
     * @return
     */
    public static String encode(String content) {
        try {
            //3.加密    DES/ECB/PKCS5Padding--->算法/工作方式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(content.getBytes());//输入要加密的内容
            return Hex.encodeHexString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     *
     * @return
     */
    public static String decode(String content) {
        try {
            //4.解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(content.getBytes());
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}