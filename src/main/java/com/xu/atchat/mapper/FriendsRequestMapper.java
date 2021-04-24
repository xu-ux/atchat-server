package com.xu.atchat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.atchat.model.domain.FriendsRequest;
import com.xu.atchat.model.dto.FriendRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 好友申请 Mapper 接口
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Repository
public interface FriendsRequestMapper extends BaseMapper<FriendsRequest> {

    /**
     * 查询发出好友申请的用户的详细信息
     * @param acceptUserId
     * @return
     */
    List<FriendRequestDTO> queryFriendRequestList(String acceptUserId);
}
