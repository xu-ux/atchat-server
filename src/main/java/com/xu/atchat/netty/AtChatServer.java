package com.xu.atchat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/4/18 14:17
 * @description netty服务
 */
@Slf4j
@Component
public class AtChatServer {


    private static class SingletonAtChatServer {
        static final AtChatServer instance = new AtChatServer();
    }

    public static AtChatServer getInstance() {
        return SingletonAtChatServer.instance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public AtChatServer() {
        //主从线程组
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        //启动器
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new AtChatServerInitializer());
    }

    public void start() {
        this.future = server.bind(9099);
        log.info("netty websocket server started success... port[9099]");
    }
}
