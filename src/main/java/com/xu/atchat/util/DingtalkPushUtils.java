package com.xu.atchat.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/7/25 18:47
 * @description 钉钉自定义机器人
 */
@Slf4j
public class DingtalkPushUtils {

    /**
     * 机器人安全密钥
     */
    private static final String secret= "xxxxxx自行申请xxxxxx";

    /**
     * https://oapi.dingtalk.com/robot/send?access_token=XXXXXX&timestamp=XXX&sign=XXX
     */
    private static final String url = "https://oapi.dingtalk.com/robot/send?access_token=xxxxx";


    /**
     * 第一步，把timestamp+"\n"+密钥当做签名字符串，使用HmacSHA256算法计算签名，然后进行Base64 encode，
     * 最后再把签名参数再进行urlEncode，得到最终的签名（需要使用UTF-8字符集）。
     * @return
     */
    private static String getQueryStringURL(){
        try {
            Long timestamp = System.currentTimeMillis();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");

            String queryStr = url.concat("&timestamp=").concat(timestamp.toString()).concat("&sign=").concat(sign);
            return queryStr;
        } catch (NoSuchAlgorithmException e) {
            log.error("钉钉获签异常",e);
        } catch (InvalidKeyException e) {
            log.error("钉钉获签异常",e);
        } catch (UnsupportedEncodingException e) {
            log.error("钉钉获签异常",e);
        }
        return "";
    }

    public static boolean sendText(String textMsg, boolean isAtAll, List<String> atPhone){
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(getQueryStringURL());
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(textMsg);
            request.setText(text);
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(atPhone);
            at.setIsAtAll(isAtAll);
            request.setAt(at);
            OapiRobotSendResponse response = client.execute(request);
            boolean success = response.isSuccess();
            return success;
        } catch (ApiException e) {
            log.error("钉钉发送text异常",e);
            return false;
        }
    }


    public static boolean sendLink(String url,String picUrl,String title,String text){
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(getQueryStringURL());
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("link");
            OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
            link.setMessageUrl(url);
            link.setPicUrl(picUrl);
            link.setTitle(title);
            link.setText(text);
            request.setLink(link);
            OapiRobotSendResponse response = client.execute(request);
            boolean success = response.isSuccess();
            return success;
        } catch (ApiException e) {
            log.error("钉钉发送link异常",e);
            return false;
        }
    }


    public static boolean sendMarkdown(String text,String title,boolean isAtAll){
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(getQueryStringURL());
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("markdown");
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle(title);
            markdown.setText(text);
            request.setMarkdown(markdown);
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setIsAtAll(isAtAll);
            request.setAt(at);
            OapiRobotSendResponse response = client.execute(request);
            boolean success = response.isSuccess();
            return success;
        } catch (ApiException e) {
            log.error("钉钉发送markdown异常",e);
            return false;
        }
    }
}
