package com.example.drg.core.exception;

public abstract class ParserException extends DRGException {
  public ParserException(String message) {
    super(message);
  }

  public ParserException(String message, Throwable cause) {
    super(message, cause);
  }

  public ParserException() {}
}
