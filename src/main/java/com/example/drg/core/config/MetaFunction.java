package com.example.drg.core.config;

import java.util.List;
import java.util.Map;

public interface MetaFunction<T> {

    List<Class<?>> getArgTypes();

    T compute(Map<String, String> params, List<?> args);

    String getMetaAlias();

    Class<T> getReturnedType();

    T getTestValue();
}
