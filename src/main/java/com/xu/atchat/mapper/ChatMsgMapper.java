package com.xu.atchat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.atchat.model.domain.ChatMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 聊天信息 Mapper 接口
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Repository
public interface ChatMsgMapper extends BaseMapper<ChatMsg> {

    /**
     * 批量签收消息
     * @param ids
     * @return
     */
    void updateBatchSignInId(@Param("ids")List<String> ids);

    /**
     *
     * @param roomId
     * @param days
     * @return
     */
    List<ChatMsg> selectChatMsgWithDays(String roomId,Integer days);

}
