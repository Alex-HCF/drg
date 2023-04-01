package com.example.drg.core;

import com.example.drg.core.config.XhtmlConverter;
import com.example.drg.core.processor.ExprEvaluator;
import com.example.drg.core.processor.ExprValidator;
import com.example.drg.core.template.Resource;
import com.example.drg.core.template.TemplateEngine;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DRGFacade {

  private final ExprEvaluator exprEvaluator;
  private final ExprValidator exprValidator;
  private final TemplateEngine templateEngine;

  /**
   * Generates a document in a specific format using predefining meta functions and the freemarker
   * template
   *
   * @param templateArchive html template with resources
   * @param params external data available in meta functions
   * @param exprs list of expressions for building a model using predefined meta functions, for
   *     example ["currDate = currDate()", "user = findUserById(param('id'))"]
   * @param converter to convert generated html with resources to a specific format (for example
   *     pdf)
   * @return generated document
   * @throws com.example.drg.core.exception.DRGException subclass
   */
  public byte[] renderDocument(
      @NonNull byte[] templateArchive,
      @NonNull Map<String, Object> params,
      @NonNull List<String> exprs,
      @NonNull XhtmlConverter converter) {
    Map<String, Object> model = exprEvaluator.calcExprs(exprs, params);
    return templateEngine.processTemplate(new Resource(templateArchive), model, converter);
  }

  /**
   * * Generates a document in a specific format using predefining meta functions and the freemarker
   * template
   *
   * @param templateArchive html template with resources
   * @param exprs list of expressions for building a model using predefined meta functions, for
   *     example ["currDate = currDate()", "user = findUserById(param('id'))"]
   * @param converter to convert generated html with resources to a specific format (for example
   *     pdf)
   * @return generated document
   * @throws com.example.drg.core.exception.DRGException subclass
   */
  public byte[] renderDocument(
      @NonNull byte[] templateArchive,
      @NonNull List<String> exprs,
      @NonNull XhtmlConverter converter) {
    return renderDocument(templateArchive, Collections.emptyMap(), exprs, converter);
  }

  /**
   * Generates a example document in a specific format using an example value from predefining meta functions and the freemarker
   * template. Validating input data without calling real logic.
   *
   * @param templateArchive html template with resources
   * @param params external data available in meta functions
   * @param exprs list of expressions for building a model using predefined meta functions, for
   *     example ["currDate = currDate()", "user = findUserById(param('id'))"]
   * @param converter to convert generated html with resources to a specific format (for example
   *     pdf)
   * @return generated document
   * @throws com.example.drg.core.exception.DRGException subclass
   */
  public byte[] validate(
      @NonNull byte[] templateArchive,
      @NonNull Set<String> params,
      @NonNull List<String> exprs,
      @NonNull XhtmlConverter converter) {
    Map<String, Object> validationModel = exprValidator.validateExprs(exprs, params);
    return templateEngine.processTemplate(
        new Resource(templateArchive), validationModel, converter);
  }

  /**
   * Generates a example document in a specific format using an example value from predefining meta functions and the freemarker
   * template. Validating input data without calling real logic.
   *
   * @param templateArchive html template with resources
   * @param exprs list of expressions for building a model using predefined meta functions, for
   *     example ["currDate = currDate()", "user = findUserById(param('id'))"]
   * @param converter to convert generated html with resources to a specific format (for example
   *     pdf)
   * @return generated document
   * @throws com.example.drg.core.exception.DRGException subclass
   */
  public byte[] validate(
      @NonNull byte[] templateArchive,
      @NonNull List<String> exprs,
      @NonNull XhtmlConverter converter) {
    return validate(templateArchive, Collections.emptySet(), exprs, converter);
  }
}
