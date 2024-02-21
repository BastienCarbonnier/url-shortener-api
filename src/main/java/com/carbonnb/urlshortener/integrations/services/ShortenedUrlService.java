package com.carbonnb.urlshortener.integrations.services;

import com.carbonnb.urlshortener.integrations.repository.ShortenedUrlRepository;
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
        String encodedFullUrl = UrlShortenerUtils.encodeToBase64Url(fullUrl);
        return this.shortenedUrlRepository.findByFullUrl(encodedFullUrl);
    }

    public Optional<ShortenedUrl> findByShortenedUrl(String shortenedUrl) {
        return this.shortenedUrlRepository.findByShortenedUrl(shortenedUrl);
    }
}
