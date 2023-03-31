package com.example.drg.function;

import com.example.drg.core.config.MetaFunction;
import com.example.drg.core.exception.MFException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class DoubleMF implements MetaFunction<Double> {

  @Override
  public Double compute(Map<String, String> params, List<?> args) {
    return Double.parseDouble((String) args.get(0));
  }

  @Override
  public String getMetaAlias() {
    return "double";
  }

  @Override
  public Class<Double> getReturnedType() {
    return Double.class;
  }

  @Override
  public Double getTestValue() {
    return 123.123;
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
