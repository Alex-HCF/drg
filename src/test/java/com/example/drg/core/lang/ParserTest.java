package com.example.drg.core.lang;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ParserTest {
    Parser parser = new Parser();
    Tokenizer tokenizer = new Tokenizer();

    @Test
    void parseExpr() {
        String expr = "  var1 = func1('hello', func2 ('bye'), func3 ())   ";
        List<Token> result = tokenizer.analysisExpr(expr);

        ExprDescriptor exprDescriptor = parser.parseExpr(result);

        assertThat(exprDescriptor).usingRecursiveComparison().isEqualTo(getExprDescriptor());
    }

    ExprDescriptor getExprDescriptor() {
        FuncDescriptor func3 = new FuncDescriptor("func3", List.of());
        FuncDescriptor func2 = new FuncDescriptor("func2", List.of("bye"));
        FuncDescriptor func1 = new FuncDescriptor("func1", List.of("hello", func2, func3));
        return new ExprDescriptor("var1", func1);
    }
}