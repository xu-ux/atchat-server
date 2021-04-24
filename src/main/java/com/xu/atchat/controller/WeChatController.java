package com.xu.atchat.controller;

import com.thoughtworks.xstream.XStream;
import com.xu.atchat.constant.wechat.MsgType;
import com.xu.atchat.model.other.wechat.ImageMessage;
import com.xu.atchat.model.other.wechat.InputMessage;
import com.xu.atchat.model.other.wechat.OutputMessage;
import com.xu.atchat.util.wechat.SerializeXmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/29 22:34
 * @description 微信callback
 */
@Slf4j
@RestController
@RequestMapping("/atchat/wechat")
public class WeChatController {


    @RequestMapping(value = "/verify", method = {RequestMethod.GET, RequestMethod.POST})
    public void signatureWeChat(
            @RequestParam(value = "signature", required = false) String signature,
            @RequestParam(value = "timestamp", required = false) String timestamp,
            @RequestParam(value = "nonce", required = false) String nonce,
            @RequestParam(value = "echostr", required = false) String echostr
            , HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("/atchat/wechat/verify 接口被调用");
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet) {

            String[] values = {"E7Tsdf4919hoL5dsq3IDYlgm73", timestamp, nonce};
            // 字典序排序
            String signStr = Arrays.stream(values).sorted().collect(Collectors.joining());
            String sign = DigestUtils.sha1Hex(signStr);

            PrintWriter writer = response.getWriter();
            // 验证成功返回ehcostr
            if (signature.equals(sign)) {
                writer.print(echostr);
            } else {
                writer.print("verify fail");
            }
            writer.flush();
            writer.close();
        }else {
            acceptMessage(request,response);
        }

    }

    private void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 处理接收消息
        ServletInputStream in = request.getInputStream();
        // 将POST流转换为XStream对象
        XStream xs = SerializeXmlUtils.createXstream();
        xs.processAnnotations(InputMessage.class);
        xs.processAnnotations(OutputMessage.class);
        // 将指定节点下的xml节点数据映射为对象
        xs.alias("xml", InputMessage.class);
        // 将流转换为字符串
        StringBuilder xmlMsg = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            xmlMsg.append(new String(b, 0, n, "UTF-8"));
        }
        // 将xml内容转换为InputMessage对象
        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());

        String servername = inputMsg.getToUserName();// 服务端
        String custermname = inputMsg.getFromUserName();// 客户端
        long createTime = inputMsg.getCreateTime();// 接收时间
        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间

        // 取得消息类型
        String msgType = inputMsg.getMsgType();
        // 根据消息类型获取对应的消息内容
        if (msgType.equals(MsgType.Text.toString())) {
            // 文本消息
            log.info("开发者微信号：" + inputMsg.getToUserName());
            log.info("发送方帐号：" + inputMsg.getFromUserName());
            log.info("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000L));
            log.info("消息内容：" + inputMsg.getContent());
            log.info("消息Id：" + inputMsg.getMsgId());

            StringBuffer str = new StringBuffer();
            str.append("<xml>");
            str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
            str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
            str.append("<CreateTime>" + returnTime + "</CreateTime>");
            str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");
            str.append("<Content><![CDATA[你说的是：" + inputMsg.getContent() + "，吗？]]></Content>");
            str.append("</xml>");
            log.info(str.toString());
            response.getWriter().write(str.toString());
        }
        // 获取并返回多图片消息
        if (msgType.equals(MsgType.Image.toString())) {
            log.info("获取多媒体信息");
            log.info("多媒体文件id：" + inputMsg.getMediaId());
            log.info("图片链接：" + inputMsg.getPicUrl());
            log.info("消息id，64位整型：" + inputMsg.getMsgId());

            OutputMessage outputMsg = new OutputMessage();
            outputMsg.setFromUserName(servername);
            outputMsg.setToUserName(custermname);
            outputMsg.setCreateTime(returnTime);
            outputMsg.setMsgType(msgType);
            ImageMessage images = new ImageMessage();
            images.setMediaId(inputMsg.getMediaId());
            outputMsg.setImage(images);
            log.info("xml转换：/n" + xs.toXML(outputMsg));
            response.getWriter().write(xs.toXML(outputMsg));
        }

        if (msgType.equals(MsgType.Event.toString())){
            log.info("获取事件信息");
            log.info("开发者微信号：" + inputMsg.getToUserName());
            log.info("发送方帐号：" + inputMsg.getFromUserName());
            log.info("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000L));
            log.info("事件类型：" + inputMsg.getEvent());
            log.error("模板事件状态："+inputMsg.getStatus());
        }
    }
}
