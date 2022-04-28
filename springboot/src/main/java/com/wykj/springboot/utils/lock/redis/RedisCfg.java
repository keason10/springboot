package com.wykj.springboot.utils.lock.redis;

import com.wykj.springboot.utils.ApplicationContextUtil;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisCfg {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Bean(name = "redisDataSource")
    @Primary
    @ConfigurationProperties(prefix = "redis")
    public RedisSourceProperties redisDataSource() {
        return new RedisSourceProperties();
    }

    @Bean
    public RedissonClient redisson(@Qualifier("redisDataSource") RedisSourceProperties properties) {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig
                .setAddress(properties.getRedisUri());
        if (StringUtils.isNotEmpty(properties.getPasswd())) {
            singleServerConfig.setPassword(properties.getPasswd());
        }
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    public static void setStr(String key, String value, long aliveSeconds) {
        RedissonClient redisson = ApplicationContextUtil.ctx.getBean(RedissonClient.class);
        RBucket<String> bucket = redisson.getBucket(key);
        bucket.set(value, aliveSeconds, TimeUnit.SECONDS);
    }

    public static String getStr(String key) {
        RedissonClient redisson = ApplicationContextUtil.ctx.getBean(RedissonClient.class);
        RBucket<String> bucket = redisson.getBucket(key);
        return bucket.get();
    }

    public static RedissonClient getClient() {
        return ApplicationContextUtil.ctx.getBean(RedissonClient.class);
    }

}
