package com.musalasoft.eventbooking.core.exception;

public class InvalidParameterValueException extends RuntimeException {

    public InvalidParameterValueException(String msg) {
        super(InvalidParameterValueException.generateMessage(msg));
    }

    private static String generateMessage(String msg) {
        return msg;
    }
}
