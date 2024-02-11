package com.carbonnb.urlshortener.utils;

import java.util.Base64;

public class UrlShortenerUtils {
    public static String encodeToBase64Url(String url) {
        return Base64.getUrlEncoder().encodeToString(url.getBytes());
    }

    public static String decodeFromBase64Url(String shortenedUrl) {
        return new String(Base64.getUrlDecoder().decode(shortenedUrl.getBytes()));
    }

    public static String shortenUrl (String uuid) {
        return encodeToBase64Url(uuid).substring(0, 9);
    }
}
