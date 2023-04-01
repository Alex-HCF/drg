package com.example.drg.core.lang;

import com.example.drg.core.exception.UnclosedBracketException;
import com.example.drg.core.exception.UnmatchedBracketException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
  Parser parser = new Parser();
  Tokenizer tokenizer = new Tokenizer();

  @Test
  void should_returnExprDescriptor_when_validTokens() {
    String expr = "  var1 = func1('hello', func2 ('bye'), func3 ())   ";
    List<Token> result = tokenizer.analysisExpr(expr);

    ExprDescriptor exprDescriptor = parser.parseExpr(result);

    assertThat(exprDescriptor).usingRecursiveComparison().isEqualTo(getExprDescriptor());
  }

  @Test
  void should_throwException_when_unmatchedBracket() {
    String expr = "  var1 = func1())   ";
    List<Token> result = tokenizer.analysisExpr(expr);

    assertThrows(UnmatchedBracketException.class, () -> parser.parseExpr(result));
  }

  @Test
  void should_throwException_when_unclosedBracket() {
    String expr = "  var1 = func1(   ";
    List<Token> result = tokenizer.analysisExpr(expr);

    assertThrows(UnclosedBracketException.class, () -> parser.parseExpr(result));
  }

  ExprDescriptor getExprDescriptor() {
    FuncDescriptor func3 = new FuncDescriptor("func3", List.of());
    FuncDescriptor func2 = new FuncDescriptor("func2", List.of("bye"));
    FuncDescriptor func1 = new FuncDescriptor("func1", List.of("hello", func2, func3));
    return new ExprDescriptor("var1", func1);
  }
}
