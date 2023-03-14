package com.example.drg.core.lang;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TokenizerTest {

    Tokenizer tokenizer = new Tokenizer();

    @Test
    void parseExpr() {
        String expr = "  var1 = func1('hello', func2 ('bye'), func3 ())   ";
        List<Token> result = tokenizer.analysisExpr(expr);

        assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(getExpected());
    }

    List<Token> getExpected() {
        return List.of(
                new Token(Token.Type.VAR, "var1"),
                new Token(Token.Type.EQUAL, "="),
                new Token(Token.Type.FUNC_NAME, "func1"),
                new Token(Token.Type.OPEN_BRACKET, "("),
                new Token(Token.Type.ARG, "'hello'"),
                new Token(Token.Type.COMMA, ","),
                new Token(Token.Type.FUNC_NAME, "func2"),
                new Token(Token.Type.OPEN_BRACKET, "("),
                new Token(Token.Type.ARG, "'bye'"),
                new Token(Token.Type.CLOSED_BRACKET, ")"),
                new Token(Token.Type.COMMA, ","),
                new Token(Token.Type.FUNC_NAME, "func3"),
                new Token(Token.Type.OPEN_BRACKET, "("),
                new Token(Token.Type.CLOSED_BRACKET, ")"),
                new Token(Token.Type.CLOSED_BRACKET, ")")
        );
    }
}