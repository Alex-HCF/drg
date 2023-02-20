package com.example.drg.core.config;

import com.example.drg.core.template.Resource;

public interface XhtmlConverter {

    byte[] convert(String xhtml, Resource resource);
}
