package com.example.drg.core;

import com.example.drg.DRGConfig;
import com.example.drg.DRGTestConfig;
import com.example.drg.converter.PdfXhtmlConverter;
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

@SpringBootTest(classes = DRGConfig.class)
@Import({DRGTestConfig.class})
class DRGFacadeTest {

  @Autowired DRGFacade drgFacade;

  @Test
  void renderDocument() throws IOException {
    byte[] archive = Files.readAllBytes(Path.of("src/test/resources/template.zip"));
    Map<String, String> params =
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
  void validate() throws IOException {
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
