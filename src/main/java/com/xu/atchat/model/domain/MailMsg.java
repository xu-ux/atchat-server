package com.xu.atchat.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 邮箱列表信息
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_mail_msg")
public class MailMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 发送邮件账户
     */
    private String sendMailId;

    /**
     * 接收邮件账户
     */
    private String acceptMailId;

    /**
     * 邮箱信息
     */
    private String mailMessage;

    /**
     * 发送时间
     */
    private LocalDateTime mailSendTime;

    /**
     * 是否签收
     */
    private Integer mailSign;


}
