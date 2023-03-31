package com.example.drg.core.exception;

public abstract class ParserException extends DRGException {
  protected ParserException(String message) {
    super(message);
  }

  protected ParserException(String message, Throwable cause) {
    super(message, cause);
  }

  protected ParserException() {}
}
