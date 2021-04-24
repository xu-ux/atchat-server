package com.xu.atchat.model.other.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.xu.atchat.annotation.XStreamCDATA;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/30 10:52
 * @description
 */
@XStreamAlias("xml")
@Getter
@Setter
public class OutputMessage {

    @XStreamAlias("ToUserName")
    @XStreamCDATA
    private String ToUserName;

    @XStreamAlias("FromUserName")
    @XStreamCDATA
    private String FromUserName;

    @XStreamAlias("CreateTime")
    private Long CreateTime;

    @XStreamAlias("MsgType")
    @XStreamCDATA
    private String MsgType = "text";

    private ImageMessage Image;
}
