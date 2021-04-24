package com.xu.atchat.netty.handler;

import com.xu.atchat.cache.ChannelCache;
import com.xu.atchat.util.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/30 23:08
 * @description 监测心跳 无需read0
 */
@Slf4j
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {


    /**
     * 用户触发事件
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ChannelCache channelCache = SpringUtils.getBean(ChannelCache.class);
        // IdleStateEvent 空闲状态事件 当channel空闲(读空闲/写空闲/读写空闲)时被触发
        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent)evt;
            // 进入读写空闲时,关闭channel
            if (idleStateEvent.state().equals(IdleState.ALL_IDLE)){
                Channel channel = ctx.channel();
                channel.close();
                log.debug("读写空闲关闭channelId:{} Channel缓存中用户数量：[{}]",channel.id(),channelCache.values().size());
            }
        }
    }
}
