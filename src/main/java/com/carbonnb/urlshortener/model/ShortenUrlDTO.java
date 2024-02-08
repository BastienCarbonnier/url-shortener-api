package com.carbonnb.urlshortener.model;

import lombok.Data;
import lombok.Setter;

import java.net.URL;
import java.util.UUID;

@Data
@Setter
public class ShortenUrlDTO {
    public ShortenUrlDTO() {
        this.id = UUID.randomUUID();
    }

    private UUID id;
    private String originalUrl;
    private URL shortenUrl;
    private Boolean alreadyShortened;
}
