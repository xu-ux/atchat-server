package com.xu.atchat.cache;

import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 20:25
 * @description
 */
@Component
public class ChannelCache implements Cache<String, Channel> {

    private  volatile ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<String, Channel>();

    @Override
    public Channel getCache(String s) {
        return channels.get(s);
    }

    @Override
    public void addCache(String s, Channel channel) {
        channels.put(s,channel);
    }

    @Override
    public void putIfAbsentCache(String s, Channel channel) {
        channels.putIfAbsent(s, channel);
    }

    @Override
    public void remove(String s) {
        channels.remove(s);
    }

    public ConcurrentHashMap values(){
        return channels;
    }
}
