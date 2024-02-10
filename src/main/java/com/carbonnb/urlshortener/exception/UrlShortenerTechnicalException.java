package com.carbonnb.urlshortener.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class UrlShortenerTechnicalException extends Exception {

    private final ErrorCodeExceptionEnum errorCode;

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerTechnicalException.class);

    public UrlShortenerTechnicalException(ErrorCodeExceptionEnum errorCode) {
        super(errorCode.getUserInfo());
        this.errorCode = errorCode;
        logger.error(errorCode.getTechnicalInfo());
    }
}
