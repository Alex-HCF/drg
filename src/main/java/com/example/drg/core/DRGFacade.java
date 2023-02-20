package com.example.drg.core;

import com.example.drg.core.config.XhtmlConverter;
import com.example.drg.core.processor.ExprProcessor;
import com.example.drg.core.template.Resource;
import com.example.drg.core.template.TemplateEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DRGFacade {

    private final ExprProcessor exprProcessor;
    private final TemplateEngine templateEngine;


    public byte[] renderDocument(byte[] template, Map<String, String> params, List<String> exprs, XhtmlConverter converter) {
        Map<String, Object> model = exprProcessor.calcExprs(exprs, params);
        return templateEngine.processTemplate(new Resource(template), model, converter);
    }

//    public byte[] validate(byte[] template, List<String> params, List<String> exprs, XhtmlConverter converter) {
//        List<ExprDescriptor> exprDescriptors = metaValidator.validateMeta(params, exprs);
//        Map<String, Object> model = exprDescriptors.stream().collect(Collectors.toMap(
//                ExprDescriptor::getVariable,
//                expr -> expr.getFuncDescriptor().getFunction().getTestValue()));
//        return templateEngine.processTemplate(new Resource(template), model, converter);
//    }

}
