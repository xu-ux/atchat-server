package com.xu.atchat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/31 12:23
 * @description
 */
@Data
@ConfigurationProperties(prefix = "min.io")
public class MinIoProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
}
