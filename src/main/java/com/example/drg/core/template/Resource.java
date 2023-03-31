package com.example.drg.core.template;

import com.example.drg.core.util.ZipMetaUtils;

import java.util.Map;

public class Resource {
  private final byte[] templateArchive;
  private final Map<String, byte[]> files;

  public Resource(byte[] templateArchive) {
    this.templateArchive = templateArchive;
    files = ZipMetaUtils.unzip(templateArchive);
  }

  public byte[] getResource(String path) {
    return files.get(path);
  }

  public byte[] getHtmlTemplate() {
    return files.get("template.html");
  }

  public byte[] getTemplateArchive() {
    return templateArchive;
  }
}
