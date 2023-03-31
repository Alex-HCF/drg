package com.example.drg.core.exception;

import lombok.Getter;

@Getter
public class ValidatorException extends RuntimeException {

  private String expr;

  public ValidatorException(String expr, Throwable cause) {
    super(String.format("Error during processing a expr %s", expr), cause);
    this.expr = expr;
  }
}
