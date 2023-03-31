package com.example.drg.core.exception;

public abstract class DRGException extends RuntimeException {
  protected DRGException() {}

  protected DRGException(String message) {
    super(message);
  }

  protected DRGException(String message, Throwable cause) {
    super(message, cause);
  }
}
