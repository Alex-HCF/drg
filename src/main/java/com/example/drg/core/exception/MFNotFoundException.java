package com.example.drg.core.exception;

import lombok.Getter;

@Getter
public class MFNotFoundException extends RuntimeException {

  private String funcName;

  public MFNotFoundException(String funcName) {
    super(String.format("Meta function %s not found", funcName));
    this.funcName = funcName;
  }
}
