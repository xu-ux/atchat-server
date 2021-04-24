package com.xu.atchat.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xu.atchat.config.EnumIntegerTypeHandler;
import com.xu.atchat.constant.FriendsRequestStatus;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/4 13:19
 * @description 好友请求信息
 */

@Getter
@Setter
public class FriendRequestDTO {

    private String id;

    private String friendUserId;

    private String friendUsername;

    private String friendNickname;

    //头像缩略图
    private String friendPortraitThumb;


    private Integer friendUsercode;

    //请求备注
    private String sendRemark;

    /**
     * 请求状态
     */
    @TableField(jdbcType = JdbcType.INTEGER,value = "friendsRequestStatus",typeHandler = EnumIntegerTypeHandler.class)
    private FriendsRequestStatus friendsRequestStatus;
}
