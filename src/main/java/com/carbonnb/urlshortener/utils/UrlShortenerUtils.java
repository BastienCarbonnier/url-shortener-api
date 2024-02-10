package com.carbonnb.urlshortener.utils;

import java.util.Base64;

public class UrlShortenerUtils {
    public static String encodeUrl (String url) {
        return Base64.getUrlEncoder().encodeToString(url.getBytes());
    }

    public static String decodeUrl (String shortenedUrl) {
        return new String(Base64.getUrlDecoder().decode(shortenedUrl.getBytes()));
    }
}
