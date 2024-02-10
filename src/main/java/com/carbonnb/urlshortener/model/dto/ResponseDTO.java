package com.carbonnb.urlshortener.model.dto;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private T data;
    private T errors;
}
