package com.carbonnb.urlshortener.model;

import lombok.Data;

import java.net.URL;

@Data
public class RequestDTO {
    private URL resourceHost;
    private String urlToShorten;
}
