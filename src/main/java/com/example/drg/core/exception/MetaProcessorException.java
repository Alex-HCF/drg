package com.example.drg.core.exception;

public class MetaProcessorException extends RuntimeException {
    public MetaProcessorException() {
    }

    public MetaProcessorException(String message) {
        super(message);
    }

    public MetaProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
