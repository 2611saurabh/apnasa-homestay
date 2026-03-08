package com.apnasahomestay.auth_service.exception;


public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) {
        super(message);
    }
}