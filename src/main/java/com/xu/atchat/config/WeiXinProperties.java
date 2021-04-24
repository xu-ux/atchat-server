package com.xu.atchat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/30 10:00
 * @description
 */
@Data
@Validated
@ConfigurationProperties(prefix = "wechat.weixin")
public class WeiXinProperties {

    private String appId;

    private String secret;

    private String grantType;

    private String tokenUrl;
}
