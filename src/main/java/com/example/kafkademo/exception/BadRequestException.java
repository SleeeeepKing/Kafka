package com.example.kafkademo.exception;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

public class BadRequestException extends ApiException {

    public HttpStatus getStatus() {
        return Objects.requireNonNullElse(status, HttpStatus.BAD_REQUEST);
    }
    public BadRequestException(ErrorCode error) {
        this(error.toString());
    }

    public BadRequestException(ErrorCode error, HttpStatus status) {
        this(error.toString(), status);
    }

    public BadRequestException(String message, Throwable e) {
        super(message, e);
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, HttpStatus status) {
        super(message, status);
    }

    public BadRequestException(ErrorCode errorCode, List<Object> args) {
        super(errorCode.toString(), args.toArray());
    }
}
