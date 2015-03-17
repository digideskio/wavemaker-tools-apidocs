/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.wavemaker.tools.apidocs.tools.core.model.ParameterType;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.parameters.BodyParameter;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.parameters.CookieParameter;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.parameters.HeaderParameter;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.parameters.PathParameter;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.parameters.QueryParameter;
import com.wavemaker.tools.apidocs.tools.parser.builder.ParameterBuilder;
import com.wavemaker.tools.apidocs.tools.parser.parser.ParameterParser;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public abstract class AbstractParameterParser implements ParameterParser {

    protected final Type dataType;
    protected final int index;
    protected final Map<Class<? extends Annotation>, Annotation> annotations;

    public AbstractParameterParser(final int index, final Type type, final Annotation[] annotations) {
        this.index = index;
        this.dataType = type;
        this.annotations = new HashMap<>();
        if (annotations != null) {
            for (Annotation annotation : annotations) {
                this.annotations.put(annotation.annotationType(), annotation);
            }
        }
    }

    @Override
    public Parameter parse() {
        Parameter parameter = null;

        ParameterType parameterType = getParameterType(annotations);

        switch (parameterType) {
            case PATH:
                parameter = new PathParameter();
                break;
            case QUERY:
                parameter = new QueryParameter();
                break;
            case HEADER:
                parameter = new HeaderParameter();
                break;
            case FORM:
                parameter = new FormParameter();
                break;
            case BODY:
                parameter = new BodyParameter();
                break;
            case COOKIE:
                parameter = new CookieParameter();
                break;
        }
        parameter.setAccess();

        return parameter;
    }

    protected Parameter postProcessBuilder(final ParameterBuilder builder) {
        builder.setIndex(index);
        builder.setEditable(true); // setting all fields as editable, lets decide in framework and resolver specific
        builder.setRequired(true);
        if (annotations.get(ApiParam.class) != null) {
            ApiParam param = (ApiParam) annotations.get(ApiParam.class);
            builder.setDescription(param.value());
            builder.setId(param.name());
        }
        handleFrameworkSpecific(annotations, builder);

        return builder.build();
    }


    @Override
    protected ParameterBuilder newBuilder() {
        return new ParameterBuilder();
    }

    protected abstract ParameterType getParameterType(Map<Class<? extends Annotation>, Annotation> annotationMap);

    protected abstract void handleFrameworkSpecific(
            Map<Class<? extends Annotation>, Annotation> annotationMap,
            ParameterBuilder builder);

}
