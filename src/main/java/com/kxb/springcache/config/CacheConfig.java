package com.kxb.springcache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author: wangwenying10
 * @date: 2022/6/17 16:46
 * @version: 1.0
 */
@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {

    @Autowired
    @Qualifier("dispatchExecutorsPool")
    private Executor cacheExecutor;

    @Bean
    public Caffeine<Object, Object> caffeineCache() {

        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(30, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(500)
                // 使用自定义线程池
                .executor(cacheExecutor)
                .removalListener(((key, value, cause) -> log.info("key:{} removed, removalCause:{}.", key, cause.name())))
                // 缓存的最大条数
                .maximumSize(1000);
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeineCache());
        // 不缓存空值
        caffeineCacheManager.setAllowNullValues(false);
        return caffeineCacheManager;
    }
}
