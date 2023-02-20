package com.example.drg.core.lang;

import com.example.drg.core.exception.MetaParserException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Parser {

    @Getter
    @AllArgsConstructor
    @ToString
    public static class ExprDescriptor {
        String varName;
        FuncDescriptor funcDescriptor;
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class FuncDescriptor {
        String funcName;
        List<?> args;
    }

    public ExprDescriptor parseExpr(List<Tokenizer.Token> tokens) {
        log.info("Starting parsing expr {}", tokens);

        String varName = tokens.get(0).getValue();

        List<Tokenizer.Token> funcTokens = tokens.subList(2, tokens.size());
        FuncDescriptor func = parseFunc(funcTokens);

        ExprDescriptor exprDescriptor = new ExprDescriptor(varName, func);

        log.info("Parse result {}", exprDescriptor);

        return exprDescriptor;
    }

    private FuncDescriptor parseFunc(List<Tokenizer.Token> tokens) {
        List<Object> args = new ArrayList<>();
        String funcName = tokens.get(0).getValue();

        int i = 2;
        while (i < tokens.size()) {
            Tokenizer.Token token = tokens.get(i);
            if (token.getType() == Tokenizer.Type.ARG) {
                args.add(parseArg(token.getValue()));
                i++;
            } else if (token.getType() == Tokenizer.Type.FUNC_NAME) {
                List<Tokenizer.Token> leftTokens = tokens.subList(i, tokens.size());
                List<Tokenizer.Token> funcTokens = parseFuncTokens(leftTokens);
                args.add(parseFunc(funcTokens));
                i += funcTokens.size();
            } else {
                i++;
            }
        }

        return new FuncDescriptor(funcName, args);
    }

    private List<Tokenizer.Token> parseFuncTokens(List<Tokenizer.Token> tokens) {
        int countOpenBracket = 0;

        int i = 0;
        while (i < tokens.size()) {
            Tokenizer.Token token = tokens.get(i);

            if (token.getType() == Tokenizer.Type.OPEN_BRACKET) {
                countOpenBracket++;
            } else if (token.getType() == Tokenizer.Type.CLOSED_BRACKET) {
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
