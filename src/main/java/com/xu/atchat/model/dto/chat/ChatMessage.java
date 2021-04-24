package com.xu.atchat.model.dto.chat;

import com.alibaba.fastjson.annotation.JSONField;
import com.xu.atchat.constant.message.MessageSign;
import com.xu.atchat.util.DateUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 17:45
 * @description 消息模型
 */
@Getter
@Setter
public class ChatMessage implements Serializable {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 发送者id
     */
    private String sendUserId;
    /**
     * 接收者id
     */
    private String acceptUserId;
    /**
     * 消息签收状态
     */
    private MessageSign messageSign;
    /**
     * 消息标记
     */
    private String messageFlag;
    /**
     * 消息主体内容
     */
    private String message;
    /**
     * 消息主键
     */
    private String chatMsgId;
    /**
     * （用户消息）发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime sendDate;
    /**
     * 需要签收消息的msg主键
     */
    private List<String> signIds;

    /**
     * 日期格式化
     * @param sendDate
     */
    public void setSendDate(String sendDate) {
        try {
            LocalDateTime parse = LocalDateTime.parse(sendDate, formatter);
            this.sendDate = parse;
        } catch (Exception e) {
            this.sendDate = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
        }
    }
}
