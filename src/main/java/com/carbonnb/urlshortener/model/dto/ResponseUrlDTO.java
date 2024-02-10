package com.carbonnb.urlshortener.model.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class ResponseUrlDTO {
    private String fullUrl;

    public ResponseUrlDTO() {}
}
