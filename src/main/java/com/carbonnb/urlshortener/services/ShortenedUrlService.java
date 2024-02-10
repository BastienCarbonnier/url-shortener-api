package com.carbonnb.urlshortener.services;

import com.carbonnb.urlshortener.repository.ShortenedUrlRepository;
import com.carbonnb.urlshortener.model.ShortenedUrl;
import com.carbonnb.urlshortener.utils.UrlShortenerUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShortenedUrlService {
    private final ShortenedUrlRepository shortenedUrlRepository;

    public ShortenedUrlService(ShortenedUrlRepository shortenedUrlRepository) {
        this.shortenedUrlRepository = shortenedUrlRepository;
    }

    public void save(ShortenedUrl shortenedUrl) {
        this.shortenedUrlRepository.save(shortenedUrl);
    }

    public Optional<ShortenedUrl> findByFullUrl(String fullUrl) {
        String encodedFullUrl = UrlShortenerUtils.encodeUrl(fullUrl);
        return this.shortenedUrlRepository.findByFullUrl(encodedFullUrl);
    }
}
