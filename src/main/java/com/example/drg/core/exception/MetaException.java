package com.example.drg.core.exception;

public class MetaException extends RuntimeException {
    public MetaException() {
    }

    public MetaException(String message) {
        super(message);
    }

    public MetaException(String message, Throwable cause) {
        super(message, cause);
    }
}
