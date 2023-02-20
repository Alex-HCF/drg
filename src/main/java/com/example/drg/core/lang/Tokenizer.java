package com.example.drg.core.lang;

import com.example.drg.core.exception.MetaTokenizerException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Tokenizer {

    @AllArgsConstructor
    @Getter
    @ToString
    public static class Token {
        Type type;
        String value;
    }

    public enum Type {
        VAR("[a-zA-Z0-9]*"),
        EQUAL("="),
        FUNC_NAME("[a-zA-Z0-9]*"),
        OPEN_BRACKET("[(]"),
        CLOSED_BRACKET("[)]"),
        ARG("'[a-zA-Z0-9]*'"),
        COMMA(",");

        private final String regex;

        public String getRegex() {
            return regex;
        }

        Type(String regex) {
            this.regex = regex;
        }
    }

    private final Map<Type, List<Type>> order = Map.of(
            Type.VAR, List.of(Type.EQUAL),
            Type.EQUAL, List.of(Type.FUNC_NAME),
            Type.FUNC_NAME, List.of(Type.OPEN_BRACKET),
            Type.OPEN_BRACKET, List.of(Type.FUNC_NAME, Type.ARG, Type.CLOSED_BRACKET),
            Type.ARG, List.of(Type.COMMA, Type.CLOSED_BRACKET),
            Type.COMMA, List.of(Type.FUNC_NAME, Type.ARG),
            Type.CLOSED_BRACKET, List.of(Type.CLOSED_BRACKET, Type.COMMA)
    );


    public List<Token> analysisExpr(String expr) {
        log.info("Starting analysis expr {}", expr);

        List<Token> tokens = new ArrayList<>();
        List<Type> expected = List.of(Type.VAR);
        int i = 0;
        while (i < expr.length()) {
            if (expr.charAt(i) == ' ') {
                i++;
                continue;
            }

            Token token;
            try {
                token = parseToken(expr.substring(i), expected);
            } catch (MetaTokenizerException e) {
                throw new MetaTokenizerException(String.format("Not found expected token for expr '%s' at position %s, expected: %s", expr, i, expected));
            }
            tokens.add(token);
            expected = order.get(token.type);

            i += token.getValue().length();
        }

        log.info("Analysis result {}", tokens);

        return tokens;
    }

    private Token parseToken(String expr, List<Type> expectedTypes) {

        List<Type> matches = expectedTypes.stream().filter(type -> expr.matches(type.getRegex())).collect(Collectors.toList());
        if (matches.size() == 1) { // found
            return new Token(matches.get(0), expr);
        } else if (matches.isEmpty() && expr.length() >= 2) {
            return parseToken(expr.substring(0, expr.length() - 1), expectedTypes);
        } else {
            throw new MetaTokenizerException();
        }
    }
}
