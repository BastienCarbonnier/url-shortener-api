package com.carbonnb.urlshortener.model.dto;

import lombok.Data;

@Data
public class RequestDTO {
    private String resourceHost;
    private String urlToShorten;
}
