package com.xu.atchat.constant.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 17:47
 * @description 消息签收状态
 */
@Getter
@AllArgsConstructor
public enum MessageSign {

    nosign(0,"未签收，未读"),

    sign(1,"已签收，已读")

    ;
    private Integer id;

    private String desc;
}
