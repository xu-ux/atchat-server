package com.xu.atchat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.atchat.model.domain.User;
import com.xu.atchat.model.dto.FriendRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
