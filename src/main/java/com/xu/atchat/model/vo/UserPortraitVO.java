package com.xu.atchat.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/4/19 19:52
 * @description 用户头像
 */
@Getter
@Setter
public class UserPortraitVO {

    @NotBlank(message = "用户主键不能为空")
    private String userId;

    @NotBlank(message = "用户头像数据不能为空")
    private String portraitData;

    private String nickname;

    @NotBlank(message = "用户名不能为空")
    private  String username;
}
