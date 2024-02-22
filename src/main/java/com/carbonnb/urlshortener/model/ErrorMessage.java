package com.carbonnb.urlshortener.model;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;

    public ErrorMessage() {}

    public ErrorMessage(int statusCode, Date timestamp, String message) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
    }
}
