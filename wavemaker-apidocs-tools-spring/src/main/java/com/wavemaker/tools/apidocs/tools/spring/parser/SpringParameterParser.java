/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;

import com.wavemaker.tools.apidocs.tools.core.model.ParameterType;
import com.wavemaker.tools.apidocs.tools.parser.builder.ParameterBuilder;
import com.wavemaker.tools.apidocs.tools.parser.impl.AbstractParameterParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class SpringParameterParser extends AbstractParameterParser {
    public SpringParameterParser(
            final int index, final Type type, final Annotation[] annotations) {
        super(index, type, annotations);
    }

    @Override
    protected void handleFrameworkSpecific(
            final Map<Class<? extends Annotation>, Annotation> annotationMap, final ParameterBuilder builder) {

        if (annotationMap.get(PathVariable.class) != null) {
            PathVariable pathVariable = (PathVariable) annotationMap.get(PathVariable.class);
            builder.setParameterType(ParameterType.PATH);
            builder.setName(pathVariable.value());
        } else if (annotationMap.get(RequestParam.class) != null) {
            RequestParam requestParam = (RequestParam) annotationMap.get(RequestParam.class);
            builder.setName(requestParam.value());
            builder.setDefaultValue(getDefaultValue(requestParam.defaultValue()));
            builder.setParameterType(ParameterType.QUERY);
            builder.setRequired(requestParam.required());
        } else if (annotationMap.get(RequestHeader.class) != null) {
            RequestHeader requestHeader = (RequestHeader) annotationMap.get(RequestHeader.class);
            builder.setName(requestHeader.value());
            builder.setDefaultValue(getDefaultValue(requestHeader.defaultValue()));
            builder.setParameterType(ParameterType.HEADER);
            builder.setRequired(requestHeader.required());
        } else if (annotationMap.get(RequestBody.class) != null) {
            RequestBody requestParam = (RequestBody) annotationMap.get(RequestBody.class);
            builder.setParameterType(ParameterType.BODY);
            builder.setRequired(requestParam.required());
            builder.setEditable(false);
        }
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
