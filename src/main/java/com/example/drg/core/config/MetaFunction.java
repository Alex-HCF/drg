package com.example.drg.core.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MetaFunction<T> {

    T compute(Map<String, String> params, List<?> args);

    String getMetaAlias();

    Class<T> getReturnedType();

    T getTestValue();

    void validateInput(List<Class<?>> argTypes, Set<String> params);
}
