/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 * <p/>
 * This software is the confidential and proprietary information of WaveMaker, Inc. You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the source code license agreement you
 * entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.Response;
import com.wavemaker.tools.apidocs.tools.core.model.TypeInformation;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.utils.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.parser.context.ResourceParserContext;
import com.wavemaker.tools.apidocs.tools.parser.parser.MethodParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.ParameterParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.PropertyParser;
import com.wavemaker.tools.apidocs.tools.parser.resolver.ParameterResolver;
import com.wavemaker.tools.apidocs.tools.parser.util.ContextUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.MethodUtils;
import com.wavemaker.tools.apidocs.tools.parser.util.TypeUtil;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 11/11/14
 */
public abstract class AbstractMethodParser implements MethodParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMethodParser.class);
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    protected final Method methodToParse;

    public AbstractMethodParser(final Method methodToParse) {
        super();
        this.methodToParse = methodToParse;
    }

    @Override
    public Operation parse() {
        Operation operation = new Operation();

        operation.setOperationUid(UUID.randomUUID().toString());
        operation.operationId(MethodUtils.generateMethodIdentifier(methodToParse));
        operation.setMethodIdentifier(MethodUtils.getMethodUniqueIdentifierId(methodToParse));

        if (methodToParse.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation apiOperation = methodToParse.getAnnotation(ApiOperation.class);
            operation.setDescription(apiOperation.value());
            operation.summary(apiOperation.notes());
        }
        if (methodToParse.isAnnotationPresent(WMAccessVisibility.class)) {
            operation.setAccessSpecifier(methodToParse.getAnnotation(WMAccessVisibility.class).value());
        } else {
            operation.setAccessSpecifier(ResourceParserContext.getContext().getSpecifier());
        }
        operation.deprecated(methodToParse.isAnnotationPresent(Deprecated.class));

        handleFrameWorkSpecific(methodToParse, operation);

        // adding api produces, consumes
        if (CollectionUtil.isEmpty(operation.getConsumes())) {
            operation.setConsumes(Lists.newArrayList(ResourceParserContext.getContext().getConsumes()));
        }
        if (CollectionUtil.isEmpty(operation.getProduces())) {
            operation.setProduces(Lists.newArrayList(ResourceParserContext.getContext().getProduces()));
        }

        parseReturnType(methodToParse, operation);
        parseParameters(methodToParse, operation);

        return operation;
    }

    protected void parseParameters(Method method, Operation operation) {
        Type[] types = method.getGenericParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();

        List<Parameter> parameters = new LinkedList<>();

        if (types != null) {
            for (int i = 0; i < types.length; i++) {
                Type type = types[i];
                final TypeInformation typeInformation = TypeUtil.extractTypeInformation(type);
                Class<?> actualType = typeInformation.isArray() ? typeInformation.getTypeArguments().get(0)
                        : typeInformation.getActualType();
                ParameterResolver resolver = ContextUtil.getConfiguration().getParameterResolvers()
                        .getResolver(actualType);
                if (resolver != null) { // doing with
                    List<Parameter> parameterList = resolver
                            .resolveParameter(i, typeInformation, annotations[i], operation);
                    parameters.addAll(parameterList);
                } else {
                    ParameterParser parser = getParameterParser(i, type, annotations[i]);
                    final Parameter parameter = parser.parse();
                    if (parameter instanceof FormParameter) {
                        operation.setConsumes(CollectionUtil.asList(MULTIPART_FORM_DATA));
                        LOGGER.info("Found form parameter, setting operation content type to {}", MULTIPART_FORM_DATA);
                    }
                    parameters.add(parameter);
                }
            }
        }

        operation.setParameters(parameters);

    }

    protected void parseReturnType(Method method, Operation operation) {
        Response response = new Response();
        PropertyParser propertyParser = new PropertyParserImpl(method.getGenericReturnType());
        response.schema(propertyParser.parse());
        response.setDescription("Success");
        operation.response(200, response); // TODO add more responses
    }


    protected abstract void handleFrameWorkSpecific(Method methodToParse, Operation operation);

    protected abstract ParameterParser getParameterParser(int index, Type type, Annotation[] annotations);

}
