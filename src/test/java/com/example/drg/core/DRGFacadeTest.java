package com.example.drg.core;

import com.example.drg.DRGConfig;
import com.example.drg.DRGTestConfig;
import com.example.drg.converter.PdfXhtmlConverter;
import com.example.drg.core.exception.EvaluatorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = DRGConfig.class)
@Import({DRGTestConfig.class})
class DRGFacadeTest {

  @Autowired DRGFacade drgFacade;

  @Test
  void should_returnDocument_when_validArgs() throws IOException {
    byte[] archive = Files.readAllBytes(Path.of("src/test/resources/template.zip"));
    Map<String, Object> params =
        Map.of(
            "first", "Hello",
            "second", "world",
            "third", "!!!");
    List<String> exprs =
        List.of(
            "currDate=currDate()",
            "tableData = testEntityList(param('first'), param('second'), param('third'), int('5'))");

    assertDoesNotThrow(
        () -> drgFacade.renderDocument(archive, params, exprs, new PdfXhtmlConverter()));
    //        byte[] result = drgFacade.renderDocument(archive, params, exprs, new
    // PdfXhtmlConverter());
    //        Files.write(Path.of("result.pdf"), result);
  }

  @Test
  void should_throwException_when_invalidExprs() throws IOException {
    byte[] archive = Files.readAllBytes(Path.of("src/test/resources/template.zip"));
    Map<String, Object> params =
        Map.of(
            "first", "Hello",
            "second", "world",
            "third", "!!!");
    List<String> exprs =
        List.of(
            "currDate=currDate(()",
            "tableData = testEntityList(param('first'), param('second'), param('third'), int('5'))");

    assertThrows(
        EvaluatorException.class,
        () -> drgFacade.renderDocument(archive, params, exprs, new PdfXhtmlConverter()));
  }

  @Test
  void should_returnExampleDocument_when_validArgs() throws IOException {
    byte[] archive = Files.readAllBytes(Path.of("src/test/resources/template.zip"));
    Set<String> params = Set.of("first", "second", "third");
    List<String> exprs =
        List.of(
            "currDate=currDate()",
            "tableData = testEntityList(param('first'), param('second'), param('third'), int('5'))");

    assertDoesNotThrow(() -> drgFacade.validate(archive, params, exprs, new PdfXhtmlConverter()));
    //        byte[] result = drgFacade.validate(archive, params, exprs, new PdfXhtmlConverter());
    //        Files.write(Path.of("result.pdf"), result);
  }
}
