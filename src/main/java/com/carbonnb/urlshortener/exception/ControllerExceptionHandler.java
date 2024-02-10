package com.carbonnb.urlshortener.exception;

import com.carbonnb.urlshortener.model.ErrorMessage;
import com.carbonnb.urlshortener.model.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(UrlShortenerTechnicalException.class)
    public ResponseEntity<ResponseDTO<ErrorMessage>> resourceNotFoundException(UrlShortenerTechnicalException ex, WebRequest request) {
        HttpStatus status = ex.getErrorCode().getHttpStatus();
        ErrorMessage message = new ErrorMessage(
                status.value(),
                new Date(),
                ex.getMessage());
        ResponseDTO<ErrorMessage> responseDTO = new ResponseDTO<>();
        responseDTO.setErrors(message);
        return new ResponseEntity<>(responseDTO, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<ErrorMessage>> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage());

        ResponseDTO<ErrorMessage> responseDTO = new ResponseDTO<>();
        responseDTO.setErrors(message);

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
