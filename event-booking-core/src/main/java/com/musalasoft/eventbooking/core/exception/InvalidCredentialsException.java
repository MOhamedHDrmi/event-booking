package com.musalasoft.eventbooking.core.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String msg) {
        super(InvalidCredentialsException.generateMessage(msg));
    }

    private static String generateMessage(String msg) {
        return msg;
    }
}
