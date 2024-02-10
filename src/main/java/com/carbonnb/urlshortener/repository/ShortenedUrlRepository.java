package com.carbonnb.urlshortener.repository;

import com.carbonnb.urlshortener.model.ShortenedUrl;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShortenedUrlRepository extends CrudRepository<ShortenedUrl, UUID> {
    Optional<ShortenedUrl> findByFullUrl(String fullUrl);
    Optional<ShortenedUrl> findByShortenedUrl(String shortenedUrl);
}