package com.xu.atchat.controller;


import com.xu.atchat.advance.response.CommonResponse;
import com.xu.atchat.annotation.RequestSingleParam;
import com.xu.atchat.model.dto.MessageDTO;
import com.xu.atchat.model.vo.FindMessageVO;
import com.xu.atchat.model.vo.MessageVO;
import com.xu.atchat.service.IChatMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 聊天信息 前端控制器
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Slf4j
@RestController
@RequestMapping("/atchat/chat-msg")
public class ChatMsgController {

    @Autowired
    IChatMsgService chatMsgService;

    /**
     * 获取自己在服务端未读的消息
     *
     * @param userId
     * @return
     */
    @PostMapping("/unread/yourself")
    public Object getUnReadMessage(@RequestSingleParam(value = "userId")  String userId){
        try {
            List<MessageVO> messageVOS = chatMsgService.unreadMessageYourself(userId);
            return new CommonResponse<List>(messageVOS);
        } catch (Exception e) {
            log.error("获取自己在服务端未读的消息失败",e);
            return null;
        }
    }

    /**
     * 获取自己在服务端所有的消息
     *
     * @param findMessageVO
     * @param days 天数
     * @return
     */
    @PostMapping("/all/yourself/{days}")
    public Object getAllMessageWithDay(@RequestBody FindMessageVO findMessageVO, @PathVariable Integer days){
        try {
           String[] strArr = {findMessageVO.getFriendId(),findMessageVO.getUserId()} ;
            String roomId = Arrays.asList(strArr).stream().sorted().collect(Collectors.joining());
            List<MessageDTO> messageDTOS = chatMsgService.allMessageYourselfWithDays(roomId, days);
            return new CommonResponse<>(messageDTOS);
        } catch (Exception e) {
            log.error("获取自己在服务端所有的消息失败",e);
            return null;
        }
    }
}
