package com.carbonnb.urlshortener.model.dto;

import com.carbonnb.urlshortener.model.ShortenedUrl;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class ShortenedUrlDTO {
    private String originalUrl;
    private String shortenedUrl;
    private Boolean alreadyShortened;

    public ShortenedUrlDTO() {}

    public ShortenedUrlDTO(ShortenedUrl shortenedUrl) {
        this.originalUrl = shortenedUrl.getOriginalUrl();
        this.shortenedUrl = shortenedUrl.getShortenedUrl();
    }
}
