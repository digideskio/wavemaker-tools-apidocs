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
package com.wavemaker.tools.apidocs.tools.spring.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ValueConstants;

import com.wavemaker.tools.apidocs.tools.core.model.ParameterType;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.BodyParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.HeaderParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.PathParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.QueryParameter;
import com.wavemaker.tools.apidocs.tools.parser.exception.ParameterParserException;
import com.wavemaker.tools.apidocs.tools.parser.impl.AbstractParameterParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class SpringParameterParser extends AbstractParameterParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringParameterParser.class);

    public SpringParameterParser(
            final int index, final Type type, final Annotation[] annotations) {
        super(index, type, annotations);
    }

    @Override
    protected ParameterType getParameterType(final Map<Class<? extends Annotation>, Annotation> annotationMap) {
        ParameterType parameterType = null;

        if (annotationMap.containsKey(PathVariable.class)) {
            parameterType = ParameterType.PATH;
        } else if (annotationMap.containsKey(RequestParam.class)) {
            parameterType = ParameterType.QUERY;
        } else if (annotationMap.containsKey(RequestHeader.class)) {
            parameterType = ParameterType.HEADER;
        }else if (annotationMap.containsKey(RequestPart.class)) {
            parameterType = ParameterType.FORM;
        } else if (annotationMap.containsKey(RequestBody.class) || annotationMap.isEmpty()) {
            parameterType = ParameterType.BODY;
        } else {
            LOGGER.error("Unknown Parameter type found, Type:{}, annonations:{}", dataType, annotationMap);
            throw new ParameterParserException("Unknown Parameter type found, Type:" + dataType +", annonations:" +
                    annotationMap);
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
    protected void handleHeaderParameter(final HeaderParameter parameter) {
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
    protected void handleFormParameter(final FormParameter parameter) {
        super.handleFormParameter(parameter);
        RequestPart requestPart = (RequestPart) annotations.get(RequestPart.class);
        if (requestPart != null) {
            parameter.name(requestPart.value());
            parameter.setRequired(requestPart.required());
        }
    }

    @Override
    protected void handleBodyParameter(final BodyParameter parameter) {
        super.handleBodyParameter(parameter);
        RequestBody requestBody = (RequestBody) annotations.get(RequestBody.class);
        if (requestBody != null) {
            parameter.setRequired(requestBody.required());
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
