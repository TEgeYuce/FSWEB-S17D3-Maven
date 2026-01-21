package com.workintech.zoo.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;


@RestControllerAdvice
@Slf4j
public class ZooGlobalExceptionHandler {
    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> handleZoo(ZooException ex) {
        log.error("ZooException -> {} ", ex.getMessage());
        ZooErrorResponse body =
                new ZooErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), System.currentTimeMillis());
        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ZooErrorResponse> handleGeneric(Exception ex) {
        log.error("Unexpected Error ", ex);
        ZooErrorResponse body =
                new ZooErrorResponse("Unexpected Error ", 500, System.currentTimeMillis());
        return ResponseEntity.status(500).body(body);
    }

}

