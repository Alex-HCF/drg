package com.example.drg.core.exception;

public abstract class TokenizerException extends DRGException {
    public TokenizerException(String message) {
        super(message);
    }

    public TokenizerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenizerException() {
    }
}
