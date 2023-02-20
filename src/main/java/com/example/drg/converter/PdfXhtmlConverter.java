package com.example.drg.converter;

import com.example.drg.core.config.XhtmlConverter;
import com.example.drg.core.template.Resource;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.styledxmlparser.resolver.resource.IResourceRetriever;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

@Service
public class PdfXhtmlConverter implements XhtmlConverter {
    @Override
    public byte[] convert(String xhtml, Resource resources) {
        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("/");
        converterProperties.setResourceRetriever(getCustomResourceRetriever(resources));
        HtmlConverter.convertToPdf(xhtml, pdf, converterProperties);
        return pdf.toByteArray();
    }

    private IResourceRetriever getCustomResourceRetriever(Resource resource) {
        return new IResourceRetriever() {
            @Override
            public InputStream getInputStreamByUrl(URL url) {
                throw new UnsupportedOperationException();
            }

            @Override
            public byte[] getByteArrayByUrl(URL url) {
                String urlStr = url.getFile();
                String path = urlStr.substring(urlStr.lastIndexOf("../") + 2);
                return resource.getResource(path);
            }
        };
    }


}
