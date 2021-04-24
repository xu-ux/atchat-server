package com.xu.atchat.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 好友申请
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_friends_request")
public class FriendsRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 好友申请发送方
     */
    private String sendUserId;

    /**
     * 好友申请接收方
     */
    private String acceptUserId;

    /**
     * 发送时间
     */
    @TableField(value = "request_time")
    private LocalDateTime requestTime;

    /**
     * 同意时间
     */
    @TableField(value = "accept_time")
    private LocalDateTime acceptTime;

    /**
     * 申请状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 发送请求备注
     */
    @TableField(value = "send_remark")
    private String sendRemark;


}
