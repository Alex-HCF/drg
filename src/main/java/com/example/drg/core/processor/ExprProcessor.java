package com.example.drg.core.processor;

import com.example.drg.core.config.MetaConfig;
import com.example.drg.core.config.MetaFunction;
import com.example.drg.core.exception.MetaProcessorException;
import com.example.drg.core.lang.Parser;
import com.example.drg.core.lang.Tokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExprProcessor {

    private final MetaConfig metaConfig;
    private final Tokenizer tokenizer;
    private final Parser parser;

    public Map<String, Object> calcExprs(List<String> exprs, Map<String, String> params) {
        return exprs.stream().map(e -> calcExpr(e, params))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, Object> calcExpr(String expr, Map<String, String> params) {
        try {
            List<Tokenizer.Token> tokens = tokenizer.analysisExpr(expr);
            Parser.ExprDescriptor exprDescriptor = parser.parseExpr(tokens);
            return Map.entry(exprDescriptor.getVarName(), calcFunc(exprDescriptor.getFuncDescriptor(), params));
        } catch (Exception e) {
            throw new MetaProcessorException(String.format("Error during processing the expr %s", expr), e);
        }
    }

    private Object calcFunc(Parser.FuncDescriptor funcDescriptor, Map<String, String> params) {
        MetaFunction<?> metaFunction = metaConfig.getMetaFunction(funcDescriptor.getFuncName());
        List<?> computeArgs = processArgs(funcDescriptor.getArgs(), metaFunction.getArgTypes(), params);
        return metaFunction.compute(params, computeArgs);
    }

    private List<?> processArgs(List<?> args, List<Class<?>> expectedArgTypes, Map<String, String> params) {
        if (args.size() != expectedArgTypes.size()) {
            throw new MetaProcessorException(String.format("Expected %s args but found %s", expectedArgTypes.size(), args.size()));
        }

        List<Object> computeArgs = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            validateArg(expectedArgTypes.get(i), args.get(i));
            computeArgs.add(computeArg(args.get(i), params));
        }

        return computeArgs;
    }

    private void validateArg(Class<?> expectedType, Object arg) {
        Class<?> actualType = getActualType(arg);
        if (!actualType.isAssignableFrom(expectedType)) {
            throw new MetaProcessorException(String.format("Expected %s but found %s", expectedType, actualType));
        }
    }


    private Object computeArg(Object arg, Map<String, String> params) {
        return arg instanceof Parser.FuncDescriptor ? calcFunc((Parser.FuncDescriptor) arg, params) : arg;
    }

    private Class<?> getActualType(Object arg) {
        return arg instanceof Parser.FuncDescriptor ?
                metaConfig.getMetaFunction(((Parser.FuncDescriptor) arg).getFuncName()).getReturnedType() :
                String.class;
    }

}
