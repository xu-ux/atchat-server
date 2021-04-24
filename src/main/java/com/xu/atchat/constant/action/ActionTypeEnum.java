package com.xu.atchat.constant.action;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 17:40
 * @description 通信动作类型 枚举名尽量在15字符内
 */
@Getter
@AllArgsConstructor
public enum ActionTypeEnum {

    /**
     * 系统管理数据
     */
    heart(0,null,"心跳数据"),
    connect(1,null,"连接netty服务器"),
    friend_add(2,null,"好友实时添加"),
    friend_req(3,null,"好友请求实时更新"),
    service_live(4,null,"服务器存活"),
    pong(5,null,"返回心跳"),

    /**
     * 业务数据
     */
    text(10,null,"文本数据"),
    img(11,null,"图片数据"),
    voice(12,null,"语音数据"),
    video(13,null,"视频数据"),
    bytes(14,null,"字节数据"),
    sign(15,null,"签收消息数据"),

    /**
     * 推送
     */


    /**
     * 群聊
     */

    /**
     * 通知
     */
    repeats_login(50,null,"重复登录"),


    /**
     * 其他
     */
    exception(40000,null,"异常"),
    other(40001,null,"其他"),
    info(40002,null,"信息"),
    warn(40003,null,"警告"),
    error(40004,null,"错误")
    ;

    private Integer id;

    private String value;

    private String desc;


}
