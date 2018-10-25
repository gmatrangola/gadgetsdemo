package com.matrangola.microcustomer.exception;

public class ResourceException extends Exception {
    private ResourceErrorResponse response;
    public ResourceException(Class<?> aClass, Long id) {
        super("Unable to find " + id + " for " + aClass.getName());
        response = new ResourceErrorResponse("Not Found", aClass.getName(), id);
    }

    public ResourceErrorResponse getResponse() {
        return response;
    }
}
