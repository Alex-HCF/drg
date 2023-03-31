package com.example.drg.core.exception;

import com.example.drg.core.lang.Token;
import lombok.Getter;

import java.util.List;

@Getter
public class UnexpectedTokenException extends TokenizerException {

  private final String expr;
  private final int pos;
  private final List<Token.Type> expected;

  public UnexpectedTokenException(String expr, int pos, List<Token.Type> expected) {
    super(
        String.format(
            "Not found expected token for expr '%s' at position %s, expected: %s",
            expr, pos, expected));
    this.expr = expr;
    this.pos = pos;
    this.expected = expected;
  }
}
