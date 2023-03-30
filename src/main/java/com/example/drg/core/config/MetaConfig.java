package com.example.drg.core.config;

import com.example.drg.core.exception.MFNotFoundException;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class MetaConfig {

    private final Map<String, MetaFunction<?>> functions;

    public MetaConfig(List<MetaFunction<?>> functions) {
        this.functions = functions.stream().collect(Collectors.toMap(MetaFunction::getMetaAlias, f -> f));
    }

    public MetaFunction<?> getMetaFunction(String funcName) {
        MetaFunction<?> metaFunction = functions.get(funcName);
        return Optional.ofNullable(metaFunction).orElseThrow(() -> new MFNotFoundException(funcName));
    }
}
