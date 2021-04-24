package com.xu.atchat.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/3 20:53
 * @description 好友搜索状态
 */
@Getter
@AllArgsConstructor
public enum FriendSearchStatus  {

    USER_NOT_EXIST(0,"用户不存在"),
    NOT_YOURSELF(1,"查找用户不能是自己"),
    ALREADY_FRIENDS(2,"已经是好友了")
    ;
    private int sta;

    private String msg;


}
