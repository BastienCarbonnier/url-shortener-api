package com.carbonnb.urlshortener.services;

import com.carbonnb.urlshortener.repository.ShortenedUrlRepository;
import com.carbonnb.urlshortener.model.ShortenedUrl;
import org.springframework.stereotype.Component;

@Component
public class ShortenedUrlService {
    private final ShortenedUrlRepository shortenedUrlRepository;

    public ShortenedUrlService(ShortenedUrlRepository shortenedUrlRepository) {
        this.shortenedUrlRepository = shortenedUrlRepository;
    }

    public void save(ShortenedUrl shortenedUrl) {
        this.shortenedUrlRepository.save(shortenedUrl.getShortenedUrl(), shortenedUrl.getOriginalUrl());
    }

    public String getShortenedUrl(String encodedUrl) {
        return this.shortenedUrlRepository.findByUrl(encodedUrl);
    }
}
