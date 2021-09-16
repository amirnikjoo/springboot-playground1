/*
package ir.shaparak.eWallet.config;

import io.lettuce.core.RedisCommandTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class RedisCacheErrorHandler implements CacheErrorHandler {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        handleTimeOutException(exception);
        log.info("Unable to get from cache " + cache.getName() + " : " + exception.getMessage());
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        handleTimeOutException(exception);
        log.info("Unable to put into cache " + cache.getName() + " : " + exception.getMessage());
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        handleTimeOutException(exception);
        log.info("Unable to evict from cache " + cache.getName() + " : " + exception.getMessage());
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        handleTimeOutException(exception);
        log.info("Unable to clean cache " + cache.getName() + " : " + exception.getMessage());
    }

    */
/**
     * We handle redis connection timeout exception.
     * If the exception is handled then it is treated as a cache miss and gets the data from actual storage
     *
     * @param exception For manage redis timeout exception
     *//*

    private void handleTimeOutException(RuntimeException exception) {
        if (exception instanceof RedisCommandTimeoutException)
            log.warn("Handled redis exception" , exception);
        else
            log.error("Unhandled redis exception" , exception);
    }
}*/
