package com.xu.atchat.model.other.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.xu.atchat.annotation.XStreamCDATA;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/30 10:53
 * @description
 */
@Getter
@Setter
public class MediaIdMessage {

    @XStreamAlias("MediaId")
    @XStreamCDATA
    private String MediaId;
}
