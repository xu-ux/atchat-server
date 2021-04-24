package com.xu.atchat;

import com.xu.atchat.netty.AtChatServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 整合netty
 *
 * @author xucl
 */
@Slf4j
@Component
public class NettyBoot implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			try {
				AtChatServer.getInstance().start();
			} catch (Exception e) {
				log.error("netty boot fail!",e);
			}
		}
	}
	
}
