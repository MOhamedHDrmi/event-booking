package com.musalasoft.eventbooking.rest.exception;

import com.musalasoft.eventbooking.core.exception.GenericException;
import com.musalasoft.eventbooking.core.exception.InvalidCredentialsException;
import com.musalasoft.eventbooking.core.exception.InvalidParameterValueException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestAPIExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException e) {
        GenericException error = new GenericException(HttpStatus.UNAUTHORIZED);
        error.setMessage(String.format(e.getMessage()));
        return buildResponseEntity(error);
    }

    @ExceptionHandler(InvalidParameterValueException.class)
    ResponseEntity<Object> handleInvalidParameterException(InvalidParameterValueException e) {
        GenericException error = new GenericException(HttpStatus.BAD_REQUEST);
        error.setMessage(String.format(e.getMessage()));
        return buildResponseEntity(error);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<Object> handleTokenViolation() {
        GenericException error = new GenericException(HttpStatus.UNAUTHORIZED);
        return buildResponseEntity(error);
    }

    private ResponseEntity<Object> buildResponseEntity(GenericException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }
}
