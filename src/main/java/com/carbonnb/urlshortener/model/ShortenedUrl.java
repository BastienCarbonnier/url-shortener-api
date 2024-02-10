package com.carbonnb.urlshortener.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShortenedUrl implements Serializable {
    String originalUrl;
    String shortenedUrl;
}