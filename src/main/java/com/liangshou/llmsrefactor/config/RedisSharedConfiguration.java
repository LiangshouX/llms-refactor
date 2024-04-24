package com.liangshou.llmsrefactor.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;

/**
 * @author X-L-S
 */
@Configuration
@EnableConfigurationProperties({RedisConfig.class})
public class RedisSharedConfiguration {

    @Bean
    public JedisPooled jedisPooled(RedisConfig redisConfig) {
        return new JedisPooled(redisConfig.url());
    }

}
