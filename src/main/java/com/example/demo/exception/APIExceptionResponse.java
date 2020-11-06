package com.example.demo.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class APIExceptionResponse {

    private final String timestamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;

    public APIExceptionResponse(Integer httpStatus, String error, String message, String path) {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = httpStatus;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
