package com.example.drg.core;

import com.example.drg.core.config.XhtmlConverter;
import com.example.drg.core.processor.ExprEvaluator;
import com.example.drg.core.processor.ExprValidator;
import com.example.drg.core.template.Resource;
import com.example.drg.core.template.TemplateEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DRGFacade {

  private final ExprEvaluator exprEvaluator;
  private final ExprValidator exprValidator;
  private final TemplateEngine templateEngine;

  public byte[] renderDocument(
      byte[] template, Map<String, String> params, List<String> exprs, XhtmlConverter converter) {
    Map<String, Object> model = exprEvaluator.calcExprs(exprs, params);
    return templateEngine.processTemplate(new Resource(template), model, converter);
  }

  public byte[] validate(
      byte[] template, Set<String> params, List<String> exprs, XhtmlConverter converter) {
    Map<String, Object> validationModel = exprValidator.validateExprs(exprs, params);
    return templateEngine.processTemplate(new Resource(template), validationModel, converter);
  }
}
