package com.carbonnb.urlshortener.model;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private T data;
    private T errors;
}
