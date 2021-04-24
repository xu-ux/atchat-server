package com.xu.atchat.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xu.atchat.constant.action.ActionTypeEnum;
import com.xu.atchat.model.dto.chat.ActionStrategyMessage;
import com.xu.atchat.model.dto.chat.ChatContent;
import com.xu.atchat.model.dto.chat.ChatMessage;
import com.xu.atchat.netty.strategy.ActionStrategy;
import com.xu.atchat.netty.strategy.FlyweightFactory;
import com.xu.atchat.service.push.IDingtalkService;
import com.xu.atchat.util.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xuchenglong
 * @version 1.0
 * @date 2020/4/13 17:34
 * @description 自定义处理器
 * <p>TextWebSocketFrame是为wbsocket处理文本信息的对象，载体是frame</p>
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 所有的客户端都在这里(固定写法)
     */
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 建立连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //连接就有channel
        Channel channel = ctx.channel();
        //链接建立后，获取客户端channel添加进channelGroup管理
        clients.add(channel);
        log.debug("建立连接,channelLongID = [" + channel.id().asLongText() + "]");

    }

    /**
     * 用户离开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //用户离开，其实会自动移除，并不需要remove
        log.debug("用户离开,channelLongID = [" + ctx.channel().id().asLongText() + "]");
        clients.remove(ctx.channel());

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {


        // 获取从客户端发来的信息
        String text = msg.text();
        Channel currentChannel = ctx.channel();
        log.debug("收到消息 =>" + text);
        // 1.获取客户端消息
        ChatContent chatContent = null;
        try {
            chatContent = JSONObject.parseObject(text, ChatContent.class);
        } catch (Exception e) {
            log.error("数据传输序列化失败，请检查客户端传输数据格式，channelId:{},error:{}",ctx.channel().id(),e.getMessage(),e);
        }

        // 2.消息类型
        ActionTypeEnum action = chatContent.getAction();

        // 3.执行策略
        ActionStrategy flyweight = FlyweightFactory.getFlyweight(action);
        ActionStrategyMessage actionStrategyMessage = flyweight.doOperation(chatContent, currentChannel);

    }


    /**
     * 异常情况先发送异常给客户端然后做移除处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ChatContent result = new ChatContent();
        result.setAction(ActionTypeEnum.exception);

        Channel currentChannel = ctx.channel();
        currentChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(result)));
        log.error("服务器异常，移除用户，id:{}",currentChannel.id(),cause);
        IDingtalkService dingtalkService = SpringUtils.getBean(IDingtalkService.class);
        dingtalkService.sendMarkdownByThrowable(cause);
        ctx.close();
        clients.remove(ctx.channel());
    }
}
