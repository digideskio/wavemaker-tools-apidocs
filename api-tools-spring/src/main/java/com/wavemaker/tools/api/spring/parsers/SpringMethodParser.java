/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.spring.parsers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wavemaker.tools.api.core.models.HTTPMethod;
import com.wavemaker.tools.api.core.models.Operation;
import com.wavemaker.tools.api.core.models.ResponseMessage;
import com.wavemaker.tools.api.core.parsers.ParameterParser;
import com.wavemaker.tools.api.core.parsers.impl.AbstractMethodParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class SpringMethodParser extends AbstractMethodParser {

    protected SpringMethodParser(final Method methodToParse) {
        super(methodToParse);
    }

    @Override
    protected void handleFrameWorkSpecific(
            final Method methodToParse, final Operation operation) {
        RequestMapping requestMapping = methodToParse.getAnnotation(RequestMapping.class);

        operation.setConsumes(new HashSet<>(Arrays.asList(requestMapping.consumes())));
        operation.setProduces(new HashSet<>(Arrays.asList(requestMapping.produces())));
        if (ArrayUtils.isNotEmpty(requestMapping.method())) {
            operation.setHttpMethod(HTTPMethod.valueOf(requestMapping.method()[0].name())); // fix this
        } else {
            operation.setHttpMethod(HTTPMethod.GET);
        }
        operation.setResponseMessages(new ArrayList<ResponseMessage>()); // TODO
        operation.setPolicy(null); // TODO
    }

    @Override
    protected ParameterParser getParameterParser(
            final int index, final Type type, final Annotation[] annotations) {
        return new SpringParameterParser(index, type, annotations);
    }

    @Override
    public boolean isRestMethod() {
        return methodToParse.isAnnotationPresent(RequestMapping.class);
    }

    @Override
    public String getPath() {
        RequestMapping requestMapping = methodToParse.getAnnotation(RequestMapping.class);
        String[] paths = requestMapping.value();
        if (ArrayUtils.isNotEmpty(paths)) {
            return requestMapping.value()[0];
        } else {
            return "";
        }
    }
}
