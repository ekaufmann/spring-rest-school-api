package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, MissingServletRequestParameterException.class })
    @ResponseBody
    protected ResponseEntity<APIExceptionResponse> illegalArgumentOrMissingParameter(Exception exception, HttpServletRequest request) {
        APIExceptionResponse body = new APIExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                exception.getCause().getMessage(),
                request.getServletPath()
        );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
