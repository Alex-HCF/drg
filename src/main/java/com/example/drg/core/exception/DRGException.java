package com.example.drg.core.exception;

public abstract class DRGException extends RuntimeException {
    public DRGException() {
    }

    public DRGException(String message) {
        super(message);
    }

    public DRGException(String message, Throwable cause) {
        super(message, cause);
    }
}
