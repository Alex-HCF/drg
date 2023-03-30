package com.example.drg.core.lang;

import com.example.drg.core.exception.UnexpectedTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

            Token token = parseTokenOrThrow(expr, expected, i);
            tokens.add(token);
            expected = order.get(token.getType());

            i += token.getValue().length();
        }

        log.info("Analysis result {}", tokens);

        return tokens;
    }

    private Token parseTokenOrThrow(String expr, List<Token.Type> expectedTypes, int startPos){
        return parseToken(expr.substring(startPos), expectedTypes, startPos)
                .orElseThrow(() -> new UnexpectedTokenException(expr, startPos, expectedTypes));
    }

    private Optional<Token> parseToken(String expr, List<Token.Type> expectedTypes, final int startPos) {
        List<Token.Type> matches = expectedTypes.stream().filter(type -> expr.matches(type.getRegex())).collect(Collectors.toList());
        if (matches.size() == 1) { // found
            return Optional.of(new Token(matches.get(0), expr, startPos));
        } else if (matches.isEmpty() && expr.length() >= 2) {
            return parseToken(expr.substring(0, expr.length() - 1), expectedTypes, startPos);
        } else {
            return Optional.empty();
        }
    }
}
