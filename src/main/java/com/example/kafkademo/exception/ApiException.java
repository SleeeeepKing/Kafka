package com.example.kafkademo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @className: ApiException
 * @description: ApiException
 * @author: Ren Junzhou
 **/
@Data
public abstract class ApiException extends RuntimeException {
    Object[] args;

    HttpStatus status;


    public ApiException(String message, Throwable e) {
        super(message, e);
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = (status != null) ? status : HttpStatus.BAD_REQUEST;
    }

    public ApiException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public ApiException(Throwable e) {
        super(e);
    }
}
