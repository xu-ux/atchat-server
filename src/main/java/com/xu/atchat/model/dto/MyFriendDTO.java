package com.xu.atchat.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/10 13:05
 * @description 我的朋友
 */
@Getter
@Setter
public class MyFriendDTO {

    private String id;

    private String friendUserId;

    private String friendUsername;

    private String friendNickname;

    //头像缩略图
    private String friendPortraitThumb;

}
