package com.carbonnb.urlshortener.model.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class ShortenedUrlDTO {
    private String fullUrl;
    private String shortenedUrl;
    private Boolean alreadyShortened = false;

    public ShortenedUrlDTO() {}
}
