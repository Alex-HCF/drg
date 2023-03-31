package com.example.drg.core.exception;

public abstract class TokenizerException extends DRGException {
  protected TokenizerException(String message) {
    super(message);
  }

  protected TokenizerException(String message, Throwable cause) {
    super(message, cause);
  }

  protected TokenizerException() {}
}
