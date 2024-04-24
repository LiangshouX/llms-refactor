package com.liangshou.llmsrefactor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author X-L-S
 */
@ConfigurationProperties(prefix = "redis")
public record RedisConfig (String url, String indexName) {
}
