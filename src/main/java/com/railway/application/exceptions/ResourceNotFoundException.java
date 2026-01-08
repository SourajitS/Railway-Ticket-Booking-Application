package com.railway.application.exceptions;

public class ResourceNotFoundException extends RuntimeException{



    public ResourceNotFoundException(String message) {
        super(message);
    }
}
