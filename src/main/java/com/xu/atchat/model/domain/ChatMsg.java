package com.xu.atchat.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 聊天信息
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_chat_msg")
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 发送人
     */
    private String sendUserId;

    /**
     * 接收人
     */
    private String acceptUserId;

    /**
     * 消息主体
     */
    private String message;

    /**
     * 消息标记
     * TEXT:"text", //(10,null,"文本数据"),
     * 	IMG:"img", //(11,null,"图片数据"),
     * 	VOICE:"voice", //(12,null,"语音数据"),
     * 	VIDEO:"video", //(13,null,"视频数据"),
     * 	BYTES:"bytes", //(14,null,"字节数据"),
     * 	SIGN:"sign",  //(15,null,"签收消息数据")
     * 	PONG:"pong",
     */
    private String messageFlag;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 签收状态，是否已读
     */
    private Integer signFlag;

    /**
     * 签收时间
     */
    private LocalDateTime signTime;
    /**
     *
     * 聊天室id
     */
    @TableField(value ="room_id")
    private String roomId;
    /**
     * 消息状态
     */
    @TableField(value ="status")
    private Integer status;


}
