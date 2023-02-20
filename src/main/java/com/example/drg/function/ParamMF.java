package com.example.drg.function;

import com.example.drg.core.config.MetaFunction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ParamMF implements MetaFunction<String> {

    @Override
    public List<Class<?>> getArgTypes() {
        return List.of(String.class);
    }

    @Override
    public String compute(Map<String, String> params, List<?> args) {
        return params.get(args.get(0));
    }

    @Override
    public String getMetaAlias() {
        return "param";
    }

    @Override
    public Class<String> getReturnedType() {
        return String.class;
    }

    @Override
    public String getTestValue() {
        return "testParam";
    }
}
