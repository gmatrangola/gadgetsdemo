package com.matrangola.microcustomer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<ResourceErrorResponse> resourceNotFound(ResourceException e) {
        ResourceErrorResponse response = e.getResponse();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<NoSuchElementResponse> noSuchElement(NoSuchElementException e) {
        NoSuchElementResponse notFound = new NoSuchElementResponse(e.getLocalizedMessage());
        return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
    }
}
