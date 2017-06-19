/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tools.apidocs.tools.parser.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Optional;
import com.wavemaker.tools.apidocs.tools.core.model.ArrayModel;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.ParameterType;
import com.wavemaker.tools.apidocs.tools.core.model.RefModel;
import com.wavemaker.tools.apidocs.tools.core.model.TypeInformation;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.AbstractParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.AbstractSerializableParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.BodyParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.CookieParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.HeaderParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.PathParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.QueryParameter;
import com.wavemaker.tools.apidocs.tools.core.model.properties.ArrayProperty;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.core.model.properties.RefProperty;
import com.wavemaker.tools.apidocs.tools.core.model.properties.StringProperty;
import com.wavemaker.tools.apidocs.tools.parser.parser.ParameterParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.PropertyParser;
import com.wavemaker.tools.apidocs.tools.parser.util.ContextUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.DataTypeUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.TypeUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.Utils;
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
                handleSerializableParameter((PathParameter) parameter);
                handlePathParameter((PathParameter) parameter);
                break;
            case QUERY:
                parameter = new QueryParameter();
                handleSerializableParameter((QueryParameter) parameter);
                handleQueryParameter((QueryParameter) parameter);
                break;
            case HEADER:
                parameter = new HeaderParameter();
                handleSerializableParameter((HeaderParameter) parameter);
                handleHeaderParameter((HeaderParameter) parameter);
                break;
            case FORM:
                parameter = new FormParameter();
                handleSerializableParameter((FormParameter) parameter);
                handleFormParameter((FormParameter) parameter);
                break;
            case BODY:
                parameter = new BodyParameter();
                handleBodyParameter((BodyParameter) parameter);
                break;
            case COOKIE:
                parameter = new CookieParameter();
                handleSerializableParameter((CookieParameter) parameter);
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

        TypeInformation typeInformation = TypeUtil.extractTypeInformation(dataType);
        if (Utils.isArray(typeInformation)) {
            ((AbstractParameter) parameter).setFullyQualifiedType(DataTypeUtil.getFullyQualifiedName(
                    Utils.getArrayTypeArgument(typeInformation)));
        } else {
            ((AbstractParameter) parameter).setFullyQualifiedType(DataTypeUtil.getFullyQualifiedName(
                    typeInformation.getActualType()));
        }
        if (parameter instanceof FormParameter) {
            ((FormParameter) parameter).setSchema(getModel(typeInformation));
        }
        ((AbstractParameter) parameter).setEditable(ContextUtil.getConfiguration().isEditable());
        ((AbstractParameter) parameter).setUuid(UUID.randomUUID().toString());

        return parameter;
    }

    protected <T extends AbstractSerializableParameter<T>> void handleSerializableParameter
            (AbstractSerializableParameter<T> parameter) {
        PropertyParser propertyParser = new PropertyParserImpl(dataType);
        Property property = propertyParser.parse();
        if (property instanceof RefProperty) {
            property = new StringProperty();
        }
        parameter.property(property);
        parameter.setFormat(property.getFormat());
        if (property instanceof ArrayProperty) {
            parameter.items(((ArrayProperty) property).getItems());
            parameter
                    .setCollectionFormat(ContextUtil.getConfiguration().getCollectionFormat());
        }
        if (property instanceof StringProperty) {
            parameter._enum(((StringProperty) property).getEnum());
        }
    }

    protected void handlePathParameter(final PathParameter parameter) {
    }

    protected void handleHeaderParameter(HeaderParameter parameter) {
    }

    protected void handleQueryParameter(QueryParameter parameter) {
    }

    protected void handleFormParameter(FormParameter parameter) {
    }

    protected void handleCookieParameter(CookieParameter parameter) {
    }

    protected void handleBodyParameter(BodyParameter parameter) {
        TypeInformation typeInfo = TypeUtil.extractTypeInformation(dataType);
        Model schema = getModel(typeInfo);
        if (schema instanceof ArrayModel) {
            parameter.name(ContextUtil.getUniqueName(Utils.getArrayTypeArgument(typeInfo)));
        } else {
            parameter.name(ContextUtil.getUniqueName(typeInfo.getActualType()));
        }
        parameter.schema(schema);
    }

    protected Model getModel(final TypeInformation typeInfo) {
        Model schema = null;
        if (Utils.isArray(typeInfo)) {
            schema = new ArrayModel();
            PropertyParser propertyParser = new PropertyParserImpl(Utils.getArrayTypeArgument(typeInfo));
            ((ArrayModel) schema).items(propertyParser.parse());
            ((ArrayModel) schema).setIsList(!typeInfo.isArray());
        } else {
            Optional<RefModel> modelOptional = ContextUtil.parseModel(typeInfo.getActualType());
            if (modelOptional.isPresent()) {
                schema = modelOptional.get();
                List<Model> models = new LinkedList<>();
                for (final Class<?> type : typeInfo.getTypeArguments()) {
                    Optional<RefModel> typeOptional = ContextUtil.parseModel(type);
                    if (typeOptional.isPresent()) {
                        models.add(typeOptional.get());
                    }
                }
                ((RefModel) schema).setTypeArguments(models);
            }
        }
        return schema;
    }


    protected abstract ParameterType getParameterType(Map<Class<? extends Annotation>, Annotation> annotationMap);

}
