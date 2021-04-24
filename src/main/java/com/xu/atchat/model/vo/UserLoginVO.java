package com.xu.atchat.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xuchenglong
 * @version 1.0
 * @date 2020/4/17 17:34
 */
@Getter
@Setter
public class UserLoginVO {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9_-]{4,15}$",message = "请填写正确格式的用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{6,16}$",message = "请填写正确格式的密码")
    private String password;

    /**
     * 手机设备id
     */
    private String deviceId;

}
