/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.parser.impl.AbstractMethodParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.ParameterParser;

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

        operation.setConsumes(Arrays.asList(requestMapping.consumes()));
        operation.setProduces(Arrays.asList(requestMapping.produces()));

//        operation.setResponseMessages(new ArrayList<ResponseMessage>()); // TODO
//        operation.setPolicy(null); // TODO
    }

    @Override
    protected ParameterParser getParameterParser(
            final int index, final Type type, final Annotation[] annotations) {
        // TODO model should parse Type instead of Class<?>
        return new SpringParameterParser(index, TypeUtils.getRawType(type, null), annotations);
    }

    @Override
    public boolean isRestMethod() {
        return methodToParse.isAnnotationPresent(RequestMapping.class);
    }

    @Override
    public String[] getPaths() {
        RequestMapping requestMapping = methodToParse.getAnnotation(RequestMapping.class);
        return requestMapping.value();
    }

    @Override
    public String[] getHttpMethods() {
        RequestMapping requestMapping = methodToParse.getAnnotation(RequestMapping.class);
        RequestMethod[] requestMethods = requestMapping.method();
        if (ArrayUtils.isNotEmpty(requestMethods)) {
            String[] methods = new String[requestMethods.length];
            for (int i = 0; i < requestMethods.length; i++) {
                methods[i] = requestMethods[i].name();
            }
            return methods;
        } else {
            return new String[]{"GET"}; // send default methods
        }
    }
}
