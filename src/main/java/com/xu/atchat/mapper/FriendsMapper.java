package com.xu.atchat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.atchat.model.domain.Friends;
import com.xu.atchat.model.dto.MyFriendDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 好友关联 Mapper 接口
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Repository
public interface FriendsMapper extends BaseMapper<Friends>  {

    /**
     * 查询我的好友列表
     * @param userId
     * @return
     */
    List<MyFriendDTO> queryMyFriendsList(String userId);

}
