/*
package ir.shaparak.eWallet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
*/
/*
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
*//*


import java.time.Duration;

@Configuration
@EnableCaching
public class JAVACacheConfig extends CachingConfigurerSupport implements CachingConfigurer {

    @Value("${spring.redis.host:localhost}")
    private String redisHostName;

    @Value("${spring.redis.port:6379}")
    private int redisPort;

    @Value("${spring.cache.redis.time-to-live:1}")
    private int redisDataTTL;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHostName, redisPort));
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
                .entryTtl(Duration.ofHours(redisDataTTL))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java()));

        redisCacheConfiguration.usePrefix();
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new RedisCacheErrorHandler();
    }

}
*/
