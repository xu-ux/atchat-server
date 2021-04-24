package com.xu.atchat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.atchat.model.domain.ChatMsg;
import com.xu.atchat.model.dto.MessageDTO;
import com.xu.atchat.model.vo.MessageVO;

import java.util.List;

/**
 * <p>
 * 聊天信息 服务类
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
public interface IChatMsgService extends IService<ChatMsg> {

    /**
     * 获取自己在服务端未读的消息
     * @param userId
     * @return
     */
    List<MessageVO> unreadMessageYourself(String userId);

    /**
     * 获取自己在服务端未读的消息
     * @param roomId
     * @return
     */
    List<MessageDTO> allMessageYourselfWithDays(String roomId, Integer days);

}
