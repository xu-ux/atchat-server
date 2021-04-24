package com.xu.atchat.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/30 10:06
 * @description
 */
@Configuration
@EnableConfigurationProperties(value = {
        WeiXinProperties.class,
        MinIoProperties.class
})
public class PropertiesConfig {
}
