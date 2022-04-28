package com.wykj.springboot.utils.lock.redis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisSourceProperties {
    private String redisUri;
    private String passwd;
}