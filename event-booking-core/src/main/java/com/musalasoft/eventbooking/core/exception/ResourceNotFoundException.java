package com.musalasoft.eventbooking.core.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(ResourceNotFoundException.generateMessage(msg));
    }

    private static String generateMessage(String msg) {
        return msg;
    }
}
