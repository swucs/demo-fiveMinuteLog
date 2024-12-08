package com.example.demofiveminutelog.common.exception;

import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {

    private final String errorCode;

    public InvalidRequestException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
