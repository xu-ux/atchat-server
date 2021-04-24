package com.xu.atchat.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/4/18 12:39
 * @description
 */
@Getter
@Setter
public class UserRegisterVO {

    /**
     * 用户名 可以含有-_字符
     */
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9_-]{4,15}$",message = "请填写正确格式的用户名")
    private String username;

    /**
     * 密码 不能全为数字或字母，需要混合使用，可以有特殊字符
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{6,16}$",message = "请填写正确格式的密码")
    private String password;


    /**
     * 邮箱地址
     */
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$",message = "请填写正确格式的邮箱")
    private String email;

    /**
     * 手机设备id
     */
    private String deviceId;
}
