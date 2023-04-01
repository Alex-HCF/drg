package com.example.drg.core.lang;

import com.example.drg.core.exception.UnclosedBracketException;
import com.example.drg.core.exception.UnmatchedBracketException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Parser {

  private static final int ARG_START_IND = 2;
  private static final int MAIN_FUNC_START_IND = 2;

  public ExprDescriptor parseExpr(List<Token> tokens) {
    log.info("Starting parsing expr {}", tokens);

    analyseBrackets(tokens);

    String varName = parseVarName(tokens);

    List<Token> funcTokens = parseMainFunctionTokens(tokens);
    FuncDescriptor func = parseFunc(funcTokens);

    ExprDescriptor exprDescriptor = new ExprDescriptor(varName, func);

    log.info("Parse result {}", exprDescriptor);

    return exprDescriptor;
  }

  private void analyseBrackets(List<Token> tokens) {

    int countOpenBracket = 0;

    int i = MAIN_FUNC_START_IND;
    while (i < tokens.size()) {
      Token token = tokens.get(i);

      if (token.getType() == Token.Type.OPEN_BRACKET) {
        countOpenBracket++;
      } else if (token.getType() == Token.Type.CLOSED_BRACKET) {
        countOpenBracket--;
        if (isUnmatchedBracket(countOpenBracket)) {
          throw new UnmatchedBracketException(token.getOriginalPosition());
        }
      }
      i++;
    }

    if (isUnclosedBracket(countOpenBracket)) {
      throw new UnclosedBracketException(getPositionOfLastToken(tokens));
    }
  }

  private String parseVarName(List<Token> tokens) {
    return tokens.get(0).getValue();
  }

  private List<Token> parseMainFunctionTokens(List<Token> tokens) {
    return tokens.subList(2, tokens.size());
  }

  private FuncDescriptor parseFunc(List<Token> tokens) {
    List<Object> args = new ArrayList<>();
    String funcName = parseFuncName(tokens);

    int i = ARG_START_IND;
    while (i < tokens.size()) {
      Token token = tokens.get(i);
      if (token.getType() == Token.Type.ARG) {
        args.add(parseArg(token.getValue()));
        i++;
      } else if (token.getType() == Token.Type.FUNC_NAME) {
        List<Token> remainingTokens = parseRemainingTokens(tokens, i);
        List<Token> funcTokens = parseFuncTokens(remainingTokens);
        args.add(parseFunc(funcTokens));
        i += funcTokens.size();
      } else {
        i++;
      }
    }

    return new FuncDescriptor(funcName, args);
  }

  private String parseFuncName(List<Token> tokens) {
    return tokens.get(0).getValue();
  }

  private List<Token> parseRemainingTokens(List<Token> tokens, int startPos) {
    return tokens.subList(startPos, tokens.size());
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
      }
      if (isEndOfFunc(i, countOpenBracket)) {
        break;
      }
      i++;
    }

    return tokens.subList(0, i + 1);
  }

  private boolean isEndOfFunc(int position, int countOpenBracket) {
    return position > 1 && countOpenBracket == 0;
  }

  private boolean isUnmatchedBracket(int countOpenBracket) {
    return countOpenBracket < 0;
  }

  private boolean isUnclosedBracket(int countOpenBracket) {
    return countOpenBracket > 0;
  }

  private int getPositionOfLastToken(List<Token> tokens) {
    return tokens.get(tokens.size() - 1).getOriginalPosition();
  }

  private String parseArg(String arg) {
    return arg.substring(1, arg.length() - 1);
  }
}
