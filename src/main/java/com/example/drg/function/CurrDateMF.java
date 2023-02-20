package com.example.drg.function;

import com.example.drg.core.config.MetaFunction;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class CurrDateMF implements MetaFunction<Date> {
    @Override
    public List<Class<?>> getArgTypes() {
        return List.of();
    }

    @Override
    public Date compute(Map<String, String> params, List<?> args) {
        return new Date();
    }

    @Override
    public String getMetaAlias() {
        return "currDate";
    }

    @Override
    public Class<Date> getReturnedType() {
        return Date.class;
    }

    @Override
    public Date getTestValue() {
        return new Date();
    }
}
