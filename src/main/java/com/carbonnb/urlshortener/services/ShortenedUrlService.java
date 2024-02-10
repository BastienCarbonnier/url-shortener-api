package com.carbonnb.urlshortener.services;

import com.carbonnb.urlshortener.model.dto.ShortenedUrlDTO;
import com.carbonnb.urlshortener.repository.ShortenedUrlRepository;
import com.carbonnb.urlshortener.model.ShortenedUrl;
import org.springframework.stereotype.Component;

@Component
public class ShortenedUrlService {
    private final ShortenedUrlRepository shortenedUrlRepository;

    public ShortenedUrlService(ShortenedUrlRepository shortenedUrlRepository) {
        this.shortenedUrlRepository = shortenedUrlRepository;
    }

    public void save(ShortenedUrlDTO shortenedUrlDTO) {
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setFullUrl(shortenedUrlDTO.getOriginalUrl());
        shortenedUrl.setShortenedUrl(shortenedUrlDTO.getShortenedUrl());
        this.shortenedUrlRepository.save(shortenedUrl.getShortenedUrl(), shortenedUrl.getFullUrl());
    }

    public String getShortenedUrlForFullUrl(String shortUrl) {
        return this.shortenedUrlRepository.findByShortUrl(shortUrl);
    }
}
