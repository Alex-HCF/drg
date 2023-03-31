package com.example.drg.core.util;

import com.example.drg.core.exception.ZipException;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@UtilityClass
public class ZipMetaUtils {

  public Map<String, byte[]> unzip(byte[] templateArchive) {
    Map<String, byte[]> result = new HashMap<>();

    try {
      ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(templateArchive));
      ZipEntry zipEntry = zis.getNextEntry();
      while (zipEntry != null) {
        if (!zipEntry.isDirectory()) {
          ByteArrayOutputStream file = new ByteArrayOutputStream();
          file.write(zis.readAllBytes());
          result.put(zipEntry.getName(), file.toByteArray());
        }
        zipEntry = zis.getNextEntry();
      }

      zis.closeEntry();
      zis.close();

      return result;
    } catch (Exception e) {
      throw new ZipException("Error during unzip", e);
    }
  }
}
