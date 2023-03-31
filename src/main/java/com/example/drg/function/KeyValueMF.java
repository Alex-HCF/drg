package com.example.drg.function;

import com.example.drg.core.config.MetaFunction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class KeyValueMF implements MetaFunction<Map.Entry> {

  @Override
  public Map.Entry compute(Map<String, String> params, List<?> args) {
    return Map.entry(args.get(0), args.get(1));
  }

  @Override
  public Class<Map.Entry> getReturnedType() {
    return Map.Entry.class;
  }

  @Override
  public Map.Entry getTestValue() {
    return Map.entry("testKey", "testValue");
  }

  @Override
  public String getMetaAlias() {
    return "keyValue";
  }

  @Override
  public void validateInput(List<Class<?>> argTypes, Set<String> params) {}
}
