package com.xu.atchat.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/4/30 14:19
 * @description 用户名验证是否存在
 */
@Getter
@Setter
public class UserVerifyVO {

    private String userId;

    private String nickname;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9_-]{4,15}$",message = "请填写正确格式的用户名")
    private String username;
}
