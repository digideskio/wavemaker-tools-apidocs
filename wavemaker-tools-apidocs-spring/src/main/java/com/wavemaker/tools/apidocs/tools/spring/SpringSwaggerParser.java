/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.tools.apidocs.tools.core.model.Resource;
import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.runner.AnnotationSwaggerParser;
import com.wavemaker.tools.apidocs.tools.spring.parser.SpringResourceParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class SpringSwaggerParser extends AnnotationSwaggerParser {

    public SpringSwaggerParser(final SwaggerConfiguration parserConfiguration) {
        super(parserConfiguration);
    }

    @Override
    protected Set<Class<? extends Annotation>> supportedRestAnnotations() {
        Set<Class<? extends Annotation>> annotations = new HashSet<>();

        annotations.add(Controller.class);
        annotations.add(RestController.class);

        return annotations;
    }

    @Override
    protected Resource parseRestClass(final Class<?> restClass) {
        SpringResourceParser parser = new SpringResourceParser(restClass);
        return parser.parse();
    }
}
