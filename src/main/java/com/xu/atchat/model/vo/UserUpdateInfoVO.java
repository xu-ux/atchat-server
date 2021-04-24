package com.xu.atchat.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/4/19 21:06
 * @description 修改基本信息
 */
@Getter
@Setter
public class UserUpdateInfoVO {

    private String userId;

    private String nickname;

    private String username;
}
