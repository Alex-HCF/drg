package com.example.drg.core.exception;

public class TemplateEngineException extends DRGException {
  public TemplateEngineException() {}

  public TemplateEngineException(String message) {
    super(message);
  }

  public TemplateEngineException(String message, Throwable cause) {
    super(message, cause);
  }
}
