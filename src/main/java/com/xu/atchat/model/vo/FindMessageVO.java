package com.xu.atchat.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/9/26 22:43
 * @description
 */
@Getter
@Setter
public class FindMessageVO {


    /**
     * 发送者id
     */
    @NotBlank(message = "个人id不能为空")
    private String userId;
    /**
     * 接收者id
     */
    @NotBlank(message = "好友id不能为空")
    private String friendId;

    /**
     * 聊天室id
     */
    private String roomId;
}
