package com.example.demofiveminutelog.common.exception;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {

    private final String errorCode;

    public ServerException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
