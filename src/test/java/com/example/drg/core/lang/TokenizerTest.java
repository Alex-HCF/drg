package com.example.drg.core.lang;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenizerTest {

    Tokenizer tokenizer = new Tokenizer();

    @Test
    void parseExpr() {
        String expr = "  var1 = func1('hello', func2 ('bye'), func3 ())   ";
        List<Tokenizer.Token> result = tokenizer.analysisExpr(expr);

        assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(getExpected());
    }

    List<Tokenizer.Token> getExpected(){
        return List.of(
                new Tokenizer.Token(Tokenizer.Type.VAR, "var1"),
                new Tokenizer.Token(Tokenizer.Type.EQUAL, "="),
                new Tokenizer.Token(Tokenizer.Type.FUNC_NAME, "func1"),
                new Tokenizer.Token(Tokenizer.Type.OPEN_BRACKET, "("),
                new Tokenizer.Token(Tokenizer.Type.ARG, "'hello'"),
                new Tokenizer.Token(Tokenizer.Type.COMMA, ","),
                new Tokenizer.Token(Tokenizer.Type.FUNC_NAME, "func2"),
                new Tokenizer.Token(Tokenizer.Type.OPEN_BRACKET, "("),
                new Tokenizer.Token(Tokenizer.Type.ARG, "'bye'"),
                new Tokenizer.Token(Tokenizer.Type.CLOSED_BRACKET, ")"),
                new Tokenizer.Token(Tokenizer.Type.COMMA, ","),
                new Tokenizer.Token(Tokenizer.Type.FUNC_NAME, "func3"),
                new Tokenizer.Token(Tokenizer.Type.OPEN_BRACKET, "("),
                new Tokenizer.Token(Tokenizer.Type.CLOSED_BRACKET, ")"),
                new Tokenizer.Token(Tokenizer.Type.CLOSED_BRACKET, ")")
        );
    }
}