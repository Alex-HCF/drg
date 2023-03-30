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
                new Token(Token.Type.VAR, "var1", 2),
                new Token(Token.Type.EQUAL, "=", 7),
                new Token(Token.Type.FUNC_NAME, "func1", 9),
                new Token(Token.Type.OPEN_BRACKET, "(", 14),
                new Token(Token.Type.ARG, "'hello'", 15),
                new Token(Token.Type.COMMA, ",", 22),
                new Token(Token.Type.FUNC_NAME, "func2", 24),
                new Token(Token.Type.OPEN_BRACKET, "(", 30),
                new Token(Token.Type.ARG, "'bye'", 31),
                new Token(Token.Type.CLOSED_BRACKET, ")", 36),
                new Token(Token.Type.COMMA, ",", 37),
                new Token(Token.Type.FUNC_NAME, "func3", 39),
                new Token(Token.Type.OPEN_BRACKET, "(", 45),
                new Token(Token.Type.CLOSED_BRACKET, ")", 46),
                new Token(Token.Type.CLOSED_BRACKET, ")", 47)
        );
    }
}