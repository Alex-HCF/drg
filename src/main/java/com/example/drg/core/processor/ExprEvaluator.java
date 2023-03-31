package com.example.drg.core.processor;

import com.example.drg.core.config.MetaConfig;
import com.example.drg.core.config.MetaFunction;
import com.example.drg.core.exception.EvaluatorException;
import com.example.drg.core.lang.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExprEvaluator {

  private final MetaConfig metaConfig;
  private final Tokenizer tokenizer;
  private final Parser parser;

  public Map<String, Object> calcExprs(List<String> exprs, Map<String, String> params) {
    return exprs.stream()
        .map(e -> calcExpr(e, params))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private Map.Entry<String, Object> calcExpr(String expr, Map<String, String> params) {
    try {
      List<Token> tokens = tokenizer.analysisExpr(expr);
      ExprDescriptor exprDescriptor = parser.parseExpr(tokens);
      return Map.entry(
          exprDescriptor.getVarName(), calcFunc(exprDescriptor.getFuncDescriptor(), params));
    } catch (Exception e) {
      throw new EvaluatorException(expr, e);
    }
  }

  private Object calcFunc(FuncDescriptor funcDescriptor, Map<String, String> params) {
    MetaFunction<?> metaFunction = metaConfig.getMetaFunction(funcDescriptor.getFuncName());
    List<Object> processedArgs =
        funcDescriptor.getArgs().stream()
            .map(arg -> processArg(arg, params))
            .collect(Collectors.toList());

    List<Class<?>> argTypes =
        processedArgs.stream().map(Object::getClass).collect(Collectors.toList());
    metaFunction.validateInput(argTypes, params.keySet());

    return metaFunction.compute(params, processedArgs);
  }

  private Object processArg(Object arg, Map<String, String> params) {
    return arg instanceof FuncDescriptor ? calcFunc((FuncDescriptor) arg, params) : arg;
  }
}
