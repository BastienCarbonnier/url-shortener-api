package com.carbonnb.urlshortener.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ShortenedUrlRepository {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void save(String fullUrl, String shortenUrl) {
        redisTemplate.opsForValue()
                .set(fullUrl, shortenUrl);
    }

    public String findByShortUrl(String shortUrl) {
        return redisTemplate.opsForValue()
                .get(shortUrl);
    }
}