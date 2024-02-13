package com.carbonnb.urlshortener.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodeExceptionEnum {

    // General
    GENERAL_1("error.exception-backend.general", "Technical exception"),
    SHORT_URL_DOESNT_EXIST("error.exception-backend.short-url-not-exist", "Short Url doesn't exist in database", HttpStatus.NOT_FOUND);


    private final String userInfo;
    private final String technicalInfo;
    private final HttpStatus httpStatus;

    ErrorCodeExceptionEnum(String userInfo, String technicalInfo) {
        this.userInfo = userInfo;
        this.technicalInfo = technicalInfo;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    ErrorCodeExceptionEnum(String userInfo, String technicalInfo, HttpStatus httpStatus) {
        this.userInfo = userInfo;
        this.technicalInfo = technicalInfo;
        this.httpStatus = httpStatus;
    }
}
