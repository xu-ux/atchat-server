package com.xu.atchat.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xu.atchat.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/4/18 12:15
 * @description
 */
@Data
public class UserBaseDTO {

    public UserBaseDTO(){

    }


    public UserBaseDTO(User user){
        initializer(user);
    }

    private void initializer(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.usercode = user.getUsercode();
        this.nickname = user.getNickname();
        this.portraitRaw = user.getPortraitRaw();
        this.intro = user.getIntro();
        this.portraitThumb = user.getPortraitThumb();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.status = user.getStatus();
        this.accountLevel = user.getAccountLevel();
        this.coin = user.getCoin();
        this.qrcode = user.getQrcode();
        this.deviceId = user.getDeviceId();
    }

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;


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
