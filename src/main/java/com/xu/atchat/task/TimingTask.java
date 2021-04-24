package com.xu.atchat.task;

import com.xu.atchat.cache.ChannelCache;
import com.xu.atchat.service.push.IDingtalkService;
import com.xu.atchat.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/29 17:22
 * @description
 * cron 表达式 有 6域 和 7域 两种，这里只能使用6域，即7域的前6域
 */
@Slf4j
@Component
public class TimingTask {

    @Autowired
    private IDingtalkService dingtalkService;

    /**
     * 定时任务，每十分钟执行一次，发送钉钉
     *
     */
    @Scheduled(cron="0 0/10 * * * ?")
    public void doSomething() {
        log.info("定时消息，发送钉钉");
        ChannelCache channelCache = SpringUtils.getBean(ChannelCache.class);
        if (channelCache != null){
            ConcurrentHashMap values = channelCache.values();
            int size = values.size();
            Object collect = null;
            if (size > 10){
                collect = values.keySet().stream().limit(10).map(s -> s.toString()).collect(Collectors.joining("\n"));
            }else {
                collect = values.keySet().stream().map(s -> s.toString()).collect(Collectors.joining("\n"));
            }
            dingtalkService.sendTextMessage("测试环境当前在线用户数量：["+size+"]\n"+collect.toString());
        }
    }

}
