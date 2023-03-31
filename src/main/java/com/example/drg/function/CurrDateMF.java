package com.example.drg.function;

import com.example.drg.core.config.MetaFunction;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CurrDateMF implements MetaFunction<Date> {
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

  @Override
  public void validateInput(List<Class<?>> argTypes, Set<String> params) {}
}
