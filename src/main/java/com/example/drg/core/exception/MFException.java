package com.example.drg.core.exception;

public class MFException extends DRGException {
  public MFException() {}

  public MFException(String message) {
    super(message);
  }

  public MFException(String message, Throwable cause) {
    super(message, cause);
  }
}
