package com.example.drg.core.lang;

import com.example.drg.core.exception.MetaParserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Parser {

    public ExprDescriptor parseExpr(List<Token> tokens) {
        log.info("Starting parsing expr {}", tokens);

        String varName = tokens.get(0).getValue();

        List<Token> funcTokens = tokens.subList(2, tokens.size());
        FuncDescriptor func = parseFunc(funcTokens);

        ExprDescriptor exprDescriptor = new ExprDescriptor(varName, func);

        log.info("Parse result {}", exprDescriptor);

        return exprDescriptor;
    }

    private FuncDescriptor parseFunc(List<Token> tokens) {
        List<Object> args = new ArrayList<>();
        String funcName = tokens.get(0).getValue();

        int i = 2;
        while (i < tokens.size()) {
            Token token = tokens.get(i);
            if (token.getType() == Token.Type.ARG) {
                args.add(parseArg(token.getValue()));
                i++;
            } else if (token.getType() == Token.Type.FUNC_NAME) {
                List<Token> leftTokens = tokens.subList(i, tokens.size());
                List<Token> funcTokens = parseFuncTokens(leftTokens);
                args.add(parseFunc(funcTokens));
                i += funcTokens.size();
            } else {
                i++;
            }
        }

        return new FuncDescriptor(funcName, args);
    }

    private List<Token> parseFuncTokens(List<Token> tokens) {
        int countOpenBracket = 0;

        int i = 0;
        while (i < tokens.size()) {
            Token token = tokens.get(i);

            if (token.getType() == Token.Type.OPEN_BRACKET) {
                countOpenBracket++;
            } else if (token.getType() == Token.Type.CLOSED_BRACKET) {
                countOpenBracket--;
                if (countOpenBracket < 0) {
                    throw new MetaParserException("Unmatched open bracket");
                }
            }
            if (i > 1 && countOpenBracket == 0) {
                break;
            }
            i++;
        }

        if (countOpenBracket > 0) {
            throw new MetaParserException("Unclosed bracket");
        }

        return tokens.subList(0, i + 1);
    }

    private String parseArg(String arg) {
        return arg.substring(1, arg.length() - 1);
    }
}
