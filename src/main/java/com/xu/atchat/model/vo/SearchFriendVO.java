package com.xu.atchat.model.vo;

import com.xu.atchat.model.domain.User;
import lombok.Data;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/3 21:18
 * @description 好友信息
 */
@Data
public class SearchFriendVO {

    public SearchFriendVO(){

    }


    public SearchFriendVO(User user){
        initializer(user);
    }

    private void initializer(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.usercode =user.getUsercode();
        this.nickname = user.getNickname();
        this.intro = user.getIntro();
        this.portraitRaw = user.getPortraitRaw();
        this.portraitThumb = user.getPortraitThumb();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.accountLevel = user.getAccountLevel();
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
     * 状态
     */
    private String status;

    /**
     * 账户等级
     */
    private Integer accountLevel;


}
