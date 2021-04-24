package com.xu.atchat.service;



import com.xu.atchat.model.domain.ChatMsg;
import com.xu.atchat.model.dto.chat.ChatContent;
import com.xu.atchat.model.dto.chat.ChatMessage;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 18:26
 * @description
 */
public interface IAsyncChatMsgService {

    /**
     * 批量修改签收id
     * @param id
     * @return
     */
    Future<Integer> updateMessageSign(List<String> id);

    /**
     * 保存聊天信息
     * @param chatContent
     * @return
     */
    Future<ChatMsg> saveChatMsg(ChatContent chatContent);
}
