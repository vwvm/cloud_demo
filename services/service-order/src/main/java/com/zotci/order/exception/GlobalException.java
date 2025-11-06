package com.zotci.order.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Throwable.class)
    public String error(Throwable ex) {
        return ex.getMessage();
    }
}
