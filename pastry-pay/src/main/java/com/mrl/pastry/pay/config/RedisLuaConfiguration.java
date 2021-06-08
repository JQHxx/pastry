package com.mrl.pastry.pay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * Redis lua script configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/22
 */
@Configuration
public class RedisLuaConfiguration {

    @Bean("decreaseItem")
    public DefaultRedisScript<Long> decreaseItem() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/decreaseItem.lua")));
        return script;
    }

    @Bean("checkItem")
    public DefaultRedisScript<Long> checkItem() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/checkItem.lua")));
        return script;
    }
}
