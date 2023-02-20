package com.example.drg.function;

import com.example.drg.core.config.MetaFunction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IntMF implements MetaFunction<Integer> {

    @Override
    public List<Class<?>> getArgTypes() {
        return List.of(String.class);
    }

    @Override
    public Integer compute(Map<String, String> params, List<?> args) {
        return Integer.parseInt((String) args.get(0));
    }

    @Override
    public String getMetaAlias() {
        return "int";
    }

    @Override
    public Class<Integer> getReturnedType() {
        return Integer.class;
    }

    @Override
    public Integer getTestValue() {
        return 123;
    }

}
