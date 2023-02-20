package com.example.drg.core.config;

import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class MetaConfig {

    private final Map<String, MetaFunction<?>> functions;

    public MetaConfig(List<MetaFunction<?>> functions) {
        this.functions = functions.stream().collect(Collectors.toMap(MetaFunction::getMetaAlias, f -> f));
    }

    public MetaFunction<?> getMetaFunction(String metaFunction) {
        return functions.get(metaFunction);
    }

    public boolean containsMetaFunction(String metaFunction) {
        return functions.containsKey(metaFunction);
    }

}
