package com.example.drg.function;

import com.example.drg.core.config.MetaFunction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ParamMF implements MetaFunction<String> {
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

  @Override
  public void validateInput(List<Class<?>> argTypes, Set<String> params) {}
}
