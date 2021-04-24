package com.xu.atchat.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/3 21:47
 * @description 自定义错误码
 */

@Getter
@AllArgsConstructor
public enum ErrorCode {
    FriendSearchError(80001,"搜索用户错误"),
    FriendAddRequestError(80002,"添加用户失败")
    ;
    private int code;

    private String msg;
}
