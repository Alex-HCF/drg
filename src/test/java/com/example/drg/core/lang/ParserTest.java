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
        List<Tokenizer.Token> result = tokenizer.analysisExpr(expr);

        Parser.ExprDescriptor exprDescriptor = parser.parseExpr(result);

        assertThat(exprDescriptor).usingRecursiveComparison().isEqualTo(getExprDescriptor());
    }

    Parser.ExprDescriptor getExprDescriptor(){
        Parser.FuncDescriptor func3 = new Parser.FuncDescriptor("func3", List.of());
        Parser.FuncDescriptor func2 = new Parser.FuncDescriptor("func2", List.of("bye"));
        Parser.FuncDescriptor func1 = new Parser.FuncDescriptor("func1", List.of("hello", func2, func3));
        return new Parser.ExprDescriptor("var1", func1);
    }
}