package com.joe.restservice.resource;

public class InvalidErrorResource {
    private String message;
    private Object error;

    public InvalidErrorResource(String message, Object error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public Object getError() {
        return error;
    }
}
