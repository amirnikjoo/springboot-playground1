package com.kian.test.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class AppConfig {

/*        @Bean
        public CacheManager cacheManager() {
            GuavaCacheManager cacheManager = new GuavaCacheManager("mycache");
            CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
                    .maximumSize(100)
                    .expireAfterWrite(10, TimeUnit.MINUTES);
            cacheManager.setCacheBuilder(cacheBuilder);
            return cacheManager;
        }
    }*/

}
