package com.carbonnb.urlshortener.model.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class ShortenedUrlDTO {
    private String originalUrl;
    private String shortenedUrl;
    private Boolean alreadyShortened;
}
