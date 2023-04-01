package com.example.drg.core.template;

import com.example.drg.core.config.XhtmlConverter;
import com.example.drg.core.exception.TemplateEngineException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class TemplateEngine {

  private static final Configuration FREEMARKER_CONFIG =
      new Configuration(Configuration.VERSION_2_3_31);

  public byte[] processTemplate(Resource template, Map<String, ?> model, XhtmlConverter converter) {
    try {
      String htmlDocument = generateXHtml(template.getHtmlTemplate(), model);
      return converter.convert(htmlDocument, template);
    } catch (IOException e) {
      throw new TemplateEngineException("Error during template reading", e);
    } catch (TemplateException e) {
      throw new TemplateEngineException("Error during template processing", e);
    }
  }

  private String generateXHtml(byte[] template, Map<String, ?> model)
      throws IOException, TemplateException {
    Template freemarkerTemplate =
        new Template(
            "", new StringReader(new String(template, StandardCharsets.UTF_8)), FREEMARKER_CONFIG);

    ByteArrayOutputStream processedHtml = processHtml(freemarkerTemplate, model);

    return prepareHtml(processedHtml.toString(StandardCharsets.UTF_8));
  }

  private ByteArrayOutputStream processHtml(Template template, Map<String, ?> model) throws TemplateException, IOException {
    ByteArrayOutputStream processedHtml = new ByteArrayOutputStream();
    template.process(model, new OutputStreamWriter(processedHtml));
    return processedHtml;
  }

  private String prepareHtml(String html) {
    Document document = Jsoup.parse(html);
    document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
    return Parser.unescapeEntities(document.html(), true);
  }
}
