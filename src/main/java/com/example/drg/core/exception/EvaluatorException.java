package com.example.drg.core.exception;

import lombok.Getter;

@Getter
public class EvaluatorException extends RuntimeException {

  private final String expr;

  public EvaluatorException(String expr, Throwable cause) {
    super(String.format("Error during processing a expr %s", expr), cause);
    this.expr = expr;
  }
}
