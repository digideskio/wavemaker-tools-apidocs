/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.impl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Optional;
import com.wavemaker.tools.apidocs.tools.core.model.ArrayModel;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.ParameterType;
import com.wavemaker.tools.apidocs.tools.core.model.RefModel;
import com.wavemaker.tools.apidocs.tools.core.model.TypeInformation;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.AbstractParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.BodyParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.CookieParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.HeaderParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.PathParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.QueryParameter;
import com.wavemaker.tools.apidocs.tools.core.model.properties.ArrayProperty;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.core.model.properties.StringProperty;
import com.wavemaker.tools.apidocs.tools.parser.context.SwaggerParserContext;
import com.wavemaker.tools.apidocs.tools.parser.parser.ParameterParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.PropertyParser;
import com.wavemaker.tools.apidocs.tools.parser.util.ContextUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.DataTypeUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.TypeUtil;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public abstract class AbstractParameterParser implements ParameterParser {

    protected final Class<?> dataType;
    protected final int index;
    protected final Map<Class<? extends Annotation>, Annotation> annotations;

    public AbstractParameterParser(final int index, final Class<?> type, final Annotation[] annotations) {
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
                handlePathParameter((PathParameter) parameter);
                break;
            case QUERY:
                parameter = new QueryParameter();
                handleQueryParameter((QueryParameter) parameter);
                break;
            case HEADER:
                parameter = new HeaderParameter();
                handleHeaderParameter((HeaderParameter) parameter);
                break;
            case FORM:
                parameter = new FormParameter();
                handleFormParameter((FormParameter) parameter);
                break;
            case BODY:
                parameter = new BodyParameter();
                handleBodyParameter((BodyParameter) parameter);
                break;
            case COOKIE:
                parameter = new CookieParameter();
                handleCookieParameter((CookieParameter) parameter);
                break;
        }

        if (annotations.get(ApiParam.class) != null) {
            ApiParam param = (ApiParam) annotations.get(ApiParam.class);
            parameter.setDescription(param.value());
            if (StringUtils.isBlank(parameter.getName())) { // setting name if not exists with param type specific
                parameter.setName(param.name());
            }
        }

        ((AbstractParameter) parameter).setFullyQualifiedType(DataTypeUtil.getName(dataType));

        return parameter;
    }

    protected void handlePathParameter(PathParameter parameter) {
        PropertyParser propertyParser = new PropertyParserImpl(dataType);
        Property property = propertyParser.parse();
        parameter.property(property);
        if (property instanceof ArrayProperty) {
            parameter.items(((ArrayProperty) property).getItems());
        }
        if (property instanceof StringProperty) {
            parameter._enum(((StringProperty) property).getEnum());
        }
        parameter
                .setCollectionFormat(SwaggerParserContext.getInstance().getConfiguration().getCollectionFormat());
    }

    protected void handleHeaderParameter(HeaderParameter parameter) {
        PropertyParser propertyParser = new PropertyParserImpl(dataType);
        Property property = propertyParser.parse();
        parameter.property(property);
        if (property instanceof ArrayProperty) {
            parameter.items(((ArrayProperty) property).getItems());
        }
        if (property instanceof StringProperty) {
            parameter._enum(((StringProperty) property).getEnum());
        }
        parameter
                .setCollectionFormat(SwaggerParserContext.getInstance().getConfiguration().getCollectionFormat());
    }

    protected void handleQueryParameter(QueryParameter parameter) {
        PropertyParser propertyParser = new PropertyParserImpl(dataType);
        Property property = propertyParser.parse();
        parameter.property(property);
        if (property instanceof ArrayProperty) {
            parameter.items(((ArrayProperty) property).getItems());
        }
        if (property instanceof StringProperty) {
            parameter._enum(((StringProperty) property).getEnum());
        }
        parameter
                .setCollectionFormat(SwaggerParserContext.getInstance().getConfiguration().getCollectionFormat());
    }

    protected void handleFormParameter(FormParameter parameter) {
        PropertyParser propertyParser = new PropertyParserImpl(dataType);
        Property property = propertyParser.parse();
        parameter.property(property);
        if (property instanceof ArrayProperty) {
            parameter.items(((ArrayProperty) property).getItems());
        }
        if (property instanceof StringProperty) {
            parameter._enum(((StringProperty) property).getEnum());
        }
        parameter
                .setCollectionFormat(SwaggerParserContext.getInstance().getConfiguration().getCollectionFormat());
    }

    protected void handleBodyParameter(BodyParameter parameter) {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(dataType);
        Model schema = null;
        if (typeInformation.isArray() || List.class.isAssignableFrom(typeInformation.getActualType())) {
            schema = new ArrayModel();
            PropertyParser propertyParser = new PropertyParserImpl(typeInformation.getTypeArguments().get(0));
            ((ArrayModel) schema).items(propertyParser.parse());
        } else {
            Optional<RefModel> modelOptional = ContextUtil.parseModel(dataType);
            if (modelOptional.isPresent()) {
                schema = modelOptional.get();
            }
        }
        parameter.schema(schema);
        parameter.name(ContextUtil.getUniqueName(dataType));
    }

    protected void handleCookieParameter(CookieParameter parameter) {
        PropertyParser propertyParser = new PropertyParserImpl(dataType);
        Property property = propertyParser.parse();
        parameter.property(property);
        if (property instanceof ArrayProperty) {
            parameter.items(((ArrayProperty) property).getItems());
        }
        if (property instanceof StringProperty) {
            parameter._enum(((StringProperty) property).getEnum());
        }
        parameter
                .setCollectionFormat(SwaggerParserContext.getInstance().getConfiguration().getCollectionFormat());
    }


    protected abstract ParameterType getParameterType(Map<Class<? extends Annotation>, Annotation> annotationMap);

}
