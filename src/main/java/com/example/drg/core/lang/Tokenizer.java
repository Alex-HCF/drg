package com.example.drg.core.lang;

import com.example.drg.core.exception.MetaTokenizerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Tokenizer {

    private final Map<Token.Type, List<Token.Type>> order = Map.of(
            Token.Type.VAR, List.of(Token.Type.EQUAL),
            Token.Type.EQUAL, List.of(Token.Type.FUNC_NAME),
            Token.Type.FUNC_NAME, List.of(Token.Type.OPEN_BRACKET),
            Token.Type.OPEN_BRACKET, List.of(Token.Type.FUNC_NAME, Token.Type.ARG, Token.Type.CLOSED_BRACKET),
            Token.Type.ARG, List.of(Token.Type.COMMA, Token.Type.CLOSED_BRACKET),
            Token.Type.COMMA, List.of(Token.Type.FUNC_NAME, Token.Type.ARG),
            Token.Type.CLOSED_BRACKET, List.of(Token.Type.CLOSED_BRACKET, Token.Type.COMMA)
    );


    public List<Token> analysisExpr(String expr) {
        log.info("Starting analysis expr {}", expr);

        List<Token> tokens = new ArrayList<>();
        List<Token.Type> expected = List.of(Token.Type.VAR);
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
            expected = order.get(token.getType());

            i += token.getValue().length();
        }

        log.info("Analysis result {}", tokens);

        return tokens;
    }

    private Token parseToken(String expr, List<Token.Type> expectedTypes) {

        List<Token.Type> matches = expectedTypes.stream().filter(type -> expr.matches(type.getRegex())).collect(Collectors.toList());
        if (matches.size() == 1) { // found
            return new Token(matches.get(0), expr);
        } else if (matches.isEmpty() && expr.length() >= 2) {
            return parseToken(expr.substring(0, expr.length() - 1), expectedTypes);
        } else {
            throw new MetaTokenizerException();
        }
    }
}
