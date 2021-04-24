package com.xu.atchat.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户详细信息（不经常变动和查询的数据）
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 性别
     */
    private String sex;

    /**
     * 注册时间
     */
    private LocalDateTime registerTime;

    /**
     * 身份证id
     */
    private String identityCard;

    /**
     * 当前住址
     */
    private String currentAddress;


}
