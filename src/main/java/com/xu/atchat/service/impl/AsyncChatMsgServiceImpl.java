package com.xu.atchat.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.atchat.constant.CCons;
import com.xu.atchat.constant.message.MessageSign;
import com.xu.atchat.mapper.ChatMsgMapper;
import com.xu.atchat.model.domain.ChatMsg;
import com.xu.atchat.model.dto.chat.ChatContent;
import com.xu.atchat.model.dto.chat.ChatMessage;
import com.xu.atchat.service.IAsyncChatMsgService;
import com.xu.atchat.util.ListAverageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 18:27
 * @description
 */
@Service
@Slf4j
public class AsyncChatMsgServiceImpl extends ServiceImpl<ChatMsgMapper, ChatMsg> implements IAsyncChatMsgService , IService<ChatMsg> {


    @Autowired
    private ChatMsgMapper chatMsgMapper;

    /**
     * 批量修改签收id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Async
    public Future<Integer> updateMessageSign(List<String> id) {
        try {
            if (CollectionUtils.isNotEmpty(id) && id.size() < CCons.IN_CONDITION){
                chatMsgMapper.updateBatchSignInId(id);
            }else if (id.size() >= CCons.IN_CONDITION) {
                List<List<String>> lists = ListAverageUtils.thousandAssign(id,null);
                lists.stream().forEach(lt -> {
                            chatMsgMapper.updateBatchSignInId(lt);
                        }
                );
            }
        } catch (Exception e) {
            log.error("异步执行签收消息失败",e);
        }
        return null;
    }

    /**
     * 保存聊天信息
     *
     * @param chatContent
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    @Async
    public Future<ChatMsg> saveChatMsg(ChatContent chatContent) {
        try {
            ChatMsg chatMsg = new ChatMsg();
            ChatMessage chatMessage = chatContent.getChatMessage();
            chatMsg.setId(chatMessage.getChatMsgId());
            chatMsg.setMessageFlag(chatContent.getAction().name());
            //emog表情 修改数据库字段编码格式 utf8mb4
            chatMsg.setMessage(chatMessage.getMessage());
            chatMsg.setAcceptUserId(chatMessage.getAcceptUserId());
            chatMsg.setSendUserId(chatMessage.getSendUserId());
            chatMsg.setSignFlag(MessageSign.nosign.getId());
            chatMsg.setSendTime(chatMessage.getSendDate());
            chatMsg.setMessageFlag(chatMessage.getMessageFlag());
            String[] arr = {chatMessage.getSendUserId(),chatMessage.getAcceptUserId()};
            String roomId = Arrays.asList(arr).stream().sorted().collect(Collectors.joining());
            chatMsg.setRoomId(roomId);
            int insert = chatMsgMapper.insert(chatMsg);
            log.info("插入记录数：[{}]",insert);
            return null;
        } catch (Exception e) {
            log.error("异步执行保存聊天记录失败，type:{}，send:{}，accept:{}，message:{}",
                    chatContent.getAction().name(),chatContent.getChatMessage().getSendUserId(),
                    chatContent.getChatMessage().getAcceptUserId(),chatContent.getChatMessage().getMessage(),e);
            return null;
        }
    }
}
