package com.elephant.basic.storage.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author chendongzhi
 * @date 10:482018/9/4 0004
 * @description redis springboot自动配置
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
@Import(RedisClient.class)
public class RedisConfig {

    @Autowired
    private RedisClient redisClient;
    @Bean
    @ConditionalOnMissingBean(RedisClient.class)
    public RedisClient redisClient(){
        return redisClient;
    }
}
