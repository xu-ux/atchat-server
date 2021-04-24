package com.xu.atchat.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户唯一码
     */
    private Integer usercode;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 简介
     */
    private String intro;

    /**
     * 头像原版
     */
    private String portraitRaw;

    /**
     * 头像缩略
     */
    private String portraitThumb;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 状态
     */
    private String status;

    /**
     * 账户等级
     */
    private Integer accountLevel;

    /**
     * 硬币
     */
    private Integer coin;

    /**
     * 扫码添加好友
     */
    private String qrcode;

    /**
     * 手机设备id
     */
    private String deviceId;


}
