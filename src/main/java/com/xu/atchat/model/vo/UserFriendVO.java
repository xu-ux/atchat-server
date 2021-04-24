package com.xu.atchat.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/3 21:13
 * @description 返回的好友基本信息
 */
@Data
public class UserFriendVO {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9_-]{4,15}$",message = "请填写正确格式的用户名")
    private String username;

    @NotBlank(message = "用户id不能为空")
    private String userId;

    private String sendRemark;
}
