package com.xu.atchat.service.push.dingtalk;

import com.xu.atchat.advance.exception.ArgumentException;
import com.xu.atchat.service.push.IDingtalkService;
import com.xu.atchat.util.DingtalkPushUtils;
import com.xu.atchat.util.FreeMarkerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.BindException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/29 17:02
 * @description
 */
@Slf4j
@Service("dingtalkService")
public class DingtalkServiceImpl implements IDingtalkService {

    /**
     * 发送文本消息
     *
     * @param throwable
     * @return
     */
    @Override
    public Boolean sendTextMessageByThrowable(Throwable throwable) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            throwable.printStackTrace(new PrintStream(baos));
            String name = throwable.getClass().getName();
            String toBaos = baos.toString();
            String message = "";
            String msg ="应用发生异常：\n".concat(name).concat("\n");
            String specifcStr = regexSpecifyStr(toBaos);
            if (StringUtils.isNoneBlank(specifcStr)){
                msg = msg+"异常位置：\n"+specifcStr;
            }
            if (throwable instanceof MissingServletRequestParameterException ||
                throwable instanceof BindException){
                message = msg.concat("请立即检查：\n").concat(throwable.getLocalizedMessage());
            }else {
                message = msg.concat("请立即检查：\n").concat(regexThrowableStr(toBaos));
            }

            boolean b = DingtalkPushUtils.sendText(message, true, null);
            if (b){
                log.info("发送消息成功，{}",message);
                return false;
            }else {
                log.info("发送消息失败，{}",message);
                return true;
            }
        } catch (Exception e) {
            log.error("消息发送失败",e);
            return null;
        }
    }

    /**
     * 根据堆栈信息发送markdown信息
     * @param throwable
     * @return
     */
    @Override
    public Boolean sendMarkdownByThrowable(Throwable throwable) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            throwable.printStackTrace(new PrintStream(baos));
            String name = throwable.getClass().getName();
            String name2 = throwable.getClass().getSimpleName();
            String toBaos = baos.toString();

            String specifcStr = regexSpecifyStr(toBaos);

            String content = "";
            if (throwable instanceof MissingServletRequestParameterException ||
                    throwable instanceof BindException ||
                        throwable instanceof ArgumentException){
                content = (throwable.getLocalizedMessage());
            }else {
                content = (regexThrowableStr(toBaos));
            }
            Map<String, Object> model = new HashMap<String, Object>();
            String title = "应用发生异常".concat(name2);
            model.put("title", title);
            model.put("error",name);
            model.put("message",throwable.getMessage() == null ? "出现null值" :throwable.getMessage());
            model.put("position",specifcStr);
            model.put("content", content);
            model.put("nowDate",new Date());
            String tplText = FreeMarkerUtils.getTplText("dingtalk.ftl", model);
            boolean b = DingtalkPushUtils.sendMarkdown(tplText,title,true);
            if (b){
                log.info("发送消息成功，{}",content);
                return false;
            }else {
                log.info("发送消息失败，{}",content);
                return true;
            }
        } catch (Exception e) {
            log.error("消息发送失败",e);
            return null;
        }
    }

    /**
     * 过滤出项目内的包
     * regular expression
     * 返回{
     *      com.xu.atchat.netty.strategy.FlyweightFactory.getFlyweight(FlyweightFactory.java:51)
     *      com.xu.atchat.netty.handler.ChatHandler.channelRead0(ChatHandler.java:93)
     *      com.xu.atchat.netty.handler.ChatHandler.channelRead0(ChatHandler.java:33)
     * }
     * @param str
     * @return
     */
    private String regexThrowableStr(String str){
        try {
           // String pattern = "(com)(\\.)(xu)(.{10,200})(\\d\\))";
            String pattern = "(com)(\\.)(xu)(.{10,200})(\\))";
            Pattern r = Pattern.compile(pattern);
            Matcher m=r.matcher(str);
            List<String> list = new ArrayList<>();
            while (m.find()) {
                list.add(m.group());
            }
            if (CollectionUtils.isEmpty(list)){
                return str;
            }
            String s = list.stream().collect(Collectors.joining("\n"));
            return s;
        } catch (Exception e) {
            return str;
        }
    }

    /**
     * 找出具体的行和类名
     * @param str
     * @return
     */
    private String regexSpecifyStr(String str){
        try {
            String pattern = "(com\\.xu.{10,200})(\\()(.{1,30}[.java])(:)(\\d{1,4})(\\))";
            Pattern r = Pattern.compile(pattern);
            Matcher m=r.matcher(str);
            if (m.find()){
                String javaName = m.group(3);
                String line = m.group(5);
                return "类名:".concat(javaName).concat(" 行数:").concat(line).concat("\n");
            }else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 发送文本消息
     *
     * @param message
     * @return
     */
    @Override
    public Boolean sendTextMessage(String message) {
        boolean b = DingtalkPushUtils.sendText(message, false, null);
        if (b){
            log.info("发送消息成功，{}",message);
            return false;
        }else {
            log.info("发送消息失败，{}",message);
            return true;
        }
    }


}
