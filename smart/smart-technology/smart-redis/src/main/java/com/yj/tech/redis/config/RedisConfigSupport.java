package com.yj.tech.redis.config;

import com.yj.tech.common.constant.basic.Strings;
import com.yj.tech.redis.constant.RedisConstants;
import com.yj.tech.redis.lock.RedisDistributedLock;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * 扩展Redis分布式锁服务配置支持
 *
 * @author fangen
 **/
@Configuration
public class RedisConfigSupport {

    protected String getRegistryKey() {
        return null;
    }

    // 默认锁定时间60s，重载该方法自定义锁定时间
    protected long getExpireAfter() {
        return RedisConstants.DEFAULT_EXPIRE_UNUSED;
    }

    @Bean(destroyMethod = "destroy")
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        String registryKey = getRegistryKey();
        if (StringUtils.isEmpty(getRegistryKey())) {
            registryKey = "lock";
        } else {
            registryKey += Strings.MINUS + "lock";
        }
        return new RedisLockRegistry(redisConnectionFactory, registryKey, getExpireAfter());
    }

    @Bean
    public RedisDistributedLock redisDistributedLock() {
        return new RedisDistributedLock();
    }
}
