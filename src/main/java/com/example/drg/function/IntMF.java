package com.example.drg.function;

import com.example.drg.core.config.MetaFunction;
import com.example.drg.core.exception.MFException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class IntMF implements MetaFunction<Integer> {

  @Override
  public Integer compute(Map<String, Object> params, List<?> args) {
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

  @Override
  public void validateInput(List<Class<?>> argTypes, Set<String> params) {
    if (argTypes.size() != 1) {
      throw new MFException(
          String.format(
              "The number of args should be no more than one, found %s", argTypes.size()));
    }

    if (argTypes.get(0) != String.class) {
      throw new MFException("The function expects arg of type String");
    }
  }
}
