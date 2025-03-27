package com.example.kafkademo.exception;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

public class InternalServerException extends ApiException {

    public HttpStatus getStatus() {
        return Objects.requireNonNullElse(status, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public InternalServerException(ErrorCode error) {
        this(error.toString());
    }

    public InternalServerException(String message, Throwable e) {
        super(message, e);
    }

    public InternalServerException(ErrorCode errorCode, Throwable e) {
        super(errorCode.toString(), e);
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(ErrorCode errorCode, List<String> args) {
        super(errorCode.toString(), args.toArray());
    }

    public InternalServerException(Throwable e) {
        super(e);
    }
}
