package com.xu.atchat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.atchat.constant.message.MessageSign;
import com.xu.atchat.model.domain.ChatMsg;
import com.xu.atchat.mapper.ChatMsgMapper;
import com.xu.atchat.model.domain.FriendsRequest;
import com.xu.atchat.model.dto.MessageDTO;
import com.xu.atchat.model.vo.MessageVO;
import com.xu.atchat.service.IChatMsgService;
import org.apache.ibatis.reflection.wrapper.BaseWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 聊天信息 服务实现类
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Service
public class ChatMsgServiceImpl extends ServiceImpl<ChatMsgMapper, ChatMsg> implements IChatMsgService {

    @Autowired
    private ChatMsgMapper chatMsgMapper;

    /**
     * 获取自己在服务端未读的消息
     *
     * @param userId
     * @return
     */
    @Override
    public List<MessageVO> unreadMessageYourself(String userId) {
        List<ChatMsg> msgList = chatMsgMapper.selectByMap(new HashMap<String, Object>(2) {{
            put("accept_user_id", userId);
            put("sign_flag", MessageSign.nosign.getId());
        }});
        // 将数据库数据转存dto模型
        List<MessageDTO> dtoList = msgList.stream().filter(Objects::nonNull).sorted(Comparator.comparing(
                ChatMsg::getSendTime).reversed()).map(chatMsg -> {
            MessageDTO vo = new MessageDTO();
            BeanUtils.copyProperties(chatMsg, vo);
            vo.setSendDate(chatMsg.getSendTime());
            vo.setType(chatMsg.getMessageFlag());
            vo.setChatMsgId(chatMsg.getId());
            return vo;
        }).collect(Collectors.toList());

        // 对发送者进行分组，并转存集合
        List<MessageVO> vos = dtoList.stream()
                .collect(Collectors.groupingBy(MessageDTO::getSendUserId, Collectors.toList())).entrySet()
                .stream().map(e -> {
                    MessageVO vo = new MessageVO();
                    vo.setSendUserId(e.getKey());
                    vo.setAcceptUserId(userId);
                    vo.setMessageList(e.getValue());
                    return vo;
                }).collect(Collectors.toList());

        return vos;
    }

    /**
     * 获取自己在服务端所有的消息
     *
     * @param roomId
     * @param days
     * @return
     */
    @Override
    public List<MessageDTO> allMessageYourselfWithDays(String roomId, Integer days) {
        List<ChatMsg> msgList = chatMsgMapper.selectChatMsgWithDays(roomId, days);
        // 将数据库数据转存dto模型
        List<MessageDTO> dtoList = msgList.stream().filter(Objects::nonNull).sorted(Comparator.comparing(
                ChatMsg::getSendTime).reversed()).map(chatMsg -> {
            MessageDTO vo = new MessageDTO();
            BeanUtils.copyProperties(chatMsg, vo);
            vo.setSendDate(chatMsg.getSendTime());
            vo.setType(chatMsg.getMessageFlag());
            vo.setChatMsgId(chatMsg.getId());
            return vo;
        }).collect(Collectors.toList());
        return dtoList;
    }
}
