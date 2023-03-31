package com.example.drg.core.processor;

import com.example.drg.core.config.MetaConfig;
import com.example.drg.core.config.MetaFunction;
import com.example.drg.core.exception.ValidatorException;
import com.example.drg.core.lang.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExprValidator {

  private final MetaConfig metaConfig;
  private final Tokenizer tokenizer;
  private final Parser parser;

  public Map<String, Object> validateExprs(List<String> exprs, Set<String> params) {
    return exprs.stream()
        .map(e -> validateExpr(e, params))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private Map.Entry<String, Object> validateExpr(String expr, Set<String> params) {
    try {
      List<Token> tokens = tokenizer.analysisExpr(expr);
      ExprDescriptor exprDescriptor = parser.parseExpr(tokens);
      validateFunc(exprDescriptor.getFuncDescriptor(), params);
      return Map.entry(
          exprDescriptor.getVarName(), getExampleValue(exprDescriptor.getFuncDescriptor()));
    } catch (Exception e) {
      throw new ValidatorException(expr, e);
    }
  }

  private Object getExampleValue(FuncDescriptor funcDescriptor) {
    return metaConfig.getMetaFunction(funcDescriptor.getFuncName()).getTestValue();
  }

  private Class<?> validateFunc(FuncDescriptor funcDescriptor, Set<String> params) {
    MetaFunction<?> metaFunction = metaConfig.getMetaFunction(funcDescriptor.getFuncName());

    List<Class<?>> argTypes =
        funcDescriptor.getArgs().stream()
            .map(arg -> validateArg(arg, params))
            .collect(Collectors.toList());

    metaFunction.validateInput(argTypes, params);

    return metaFunction.getReturnedType();
  }

  private Class<?> validateArg(Object arg, Set<String> params) {
    return arg instanceof FuncDescriptor
        ? validateFunc((FuncDescriptor) arg, params)
        : arg.getClass();
  }
}
