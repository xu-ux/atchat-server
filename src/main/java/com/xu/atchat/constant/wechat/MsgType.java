package com.xu.atchat.constant.wechat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/30 10:54
 * @description
 */
@AllArgsConstructor
@Getter
public enum MsgType {

    Text("text"),
    Image("image"),
    Music("music"),
    Video("video"),
    Voice("voice"),
    Location("location"),
    Link("link"),
    Event("event"),
    ;
    private String msgType = "";


    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return this.msgType;
    }}
