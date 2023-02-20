package com.example.drg.core.processor;

import com.example.drg.core.config.MetaConfig;
import com.example.drg.core.config.MetaFunction;
import com.example.drg.core.exception.MetaException;
import com.example.drg.core.lang.Parser;
import com.example.drg.core.lang.Tokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExprTestValueProcessor { // todo

    private final MetaConfig metaConfig;
    private final Tokenizer tokenizer;
    private final Parser parser;

    public Map<String, Object> testExprs(List<String> exprs, List<String> params) {
        return exprs.stream().map(e -> testExpr(e, params))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, Object> testExpr(String expr, List<String> params) {
        List<Tokenizer.Token> tokens = tokenizer.analysisExpr(expr);
        Parser.ExprDescriptor exprDescriptor = parser.parseExpr(tokens);
        return Map.entry(exprDescriptor.getVarName(), testFunc(exprDescriptor.getFuncDescriptor(), params));
    }

    private Object testFunc(Parser.FuncDescriptor funcDescriptor, List<String> params) {
        MetaFunction<?> metaFunction = metaConfig.getMetaFunction(funcDescriptor.getFuncName());
        processArgs(funcDescriptor.getArgs(), metaFunction.getArgTypes(), params);
        return metaFunction.getTestValue();
    }

    private void processArgs(List<?> args, List<Class<?>> expectedArgTypes, List<String> params) {
        if (args.size() != expectedArgTypes.size()) {
            throw new MetaException("");
        }

        for (int i = 0; i < args.size(); i++) {
            validateArg(expectedArgTypes.get(i), args.get(i));
            computeArg(args.get(i), params);
        }
    }

    private void validateArg(Class<?> expectedType, Object arg) {
        Class<?> actualType = getActualType(arg);
        if (!actualType.isAssignableFrom(expectedType)) {
            throw new MetaException("");
        }
    }

    private void computeArg(Object arg, List<String> params) {
        if (arg instanceof Parser.FuncDescriptor)
            testFunc((Parser.FuncDescriptor) arg, params);
    }

    private Class<?> getActualType(Object arg) {
        return arg instanceof Parser.FuncDescriptor ?
                metaConfig.getMetaFunction(((Parser.FuncDescriptor) arg).getFuncName()).getReturnedType() :
                String.class;
    }

}
