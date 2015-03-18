/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.parser;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;

import com.wavemaker.tools.apidocs.tools.core.model.ParameterType;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.BodyParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.HeaderParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.PathParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.QueryParameter;
import com.wavemaker.tools.apidocs.tools.parser.impl.AbstractParameterParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class SpringParameterParser extends AbstractParameterParser {
    public SpringParameterParser(
            final int index, final Class<?> type, final Annotation[] annotations) {
        super(index, type, annotations);
    }

    @Override
    protected ParameterType getParameterType(
            final Map<Class<? extends Annotation>, Annotation> annotationMap) {
        ParameterType parameterType = null;

        if (annotationMap.containsKey(PathVariable.class)) {
            parameterType = ParameterType.PATH;
        } else if (annotationMap.containsKey(RequestParam.class)) {
            parameterType = ParameterType.QUERY;
        } else if (annotationMap.containsKey(RequestBody.class)) {
            parameterType = ParameterType.BODY;
        } else if (annotationMap.containsKey(RequestHeader.class)) {
            parameterType = ParameterType.HEADER;
        } else { // form parameters TODO is there any alternate approach?
            parameterType = ParameterType.FORM;
        }
        return parameterType;
    }

    @Override
    protected void handlePathParameter(final PathParameter parameter) {
        super.handlePathParameter(parameter);
        PathVariable pathVariable = (PathVariable) annotations.get(PathVariable.class);
        if (pathVariable != null) {
            parameter.name(pathVariable.value());
        }

    }

    @Override
    protected void handleHeaderParameter(
            final HeaderParameter parameter) {
        super.handleHeaderParameter(parameter);
        RequestHeader requestHeader = (RequestHeader) annotations.get(RequestHeader.class);
        if (requestHeader != null) {
            parameter.name(requestHeader.value());
            parameter.setDefaultValue(getDefaultValue(requestHeader.defaultValue()));
            parameter.setRequired(requestHeader.required());
        }
    }

    @Override
    protected void handleQueryParameter(final QueryParameter parameter) {
        super.handleQueryParameter(parameter);
        RequestParam requestParam = (RequestParam) annotations.get(RequestParam.class);
        if (requestParam != null) {
            parameter.name(requestParam.value());
            parameter.setDefaultValue(getDefaultValue(requestParam.defaultValue()));
            parameter.setRequired(requestParam.required());
        }
    }

    @Override
    protected void handleBodyParameter(final BodyParameter parameter) {
        super.handleBodyParameter(parameter);
        RequestBody requestBody = (RequestBody) annotations.get(RequestBody.class);
        parameter.setRequired(requestBody.required());

    }

    /**
     * It will check for the spring default value, if it matches returns null, else given value.
     *
     * @param defaultValue value to be check.
     * @return null if it matches spring default value, else given value.
     */
    private String getDefaultValue(String defaultValue) {
        return defaultValue.equals(ValueConstants.DEFAULT_NONE) ? null : defaultValue;
    }
}
