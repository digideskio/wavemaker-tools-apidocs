/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.parsers.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.builders.PrimitiveType;
import com.wavemaker.tools.api.core.context.ApiParserContext;
import com.wavemaker.tools.api.core.context.ParserRunnerContext;
import com.wavemaker.tools.api.core.models.FoundTypesWrapper;
import com.wavemaker.tools.api.core.models.Operation;
import com.wavemaker.tools.api.core.models.Parameter;
import com.wavemaker.tools.api.core.models.TypeInformation;
import com.wavemaker.tools.api.core.models.TypeInformationWrapper;
import com.wavemaker.tools.api.core.parsers.MethodParser;
import com.wavemaker.tools.api.core.parsers.ParameterParser;
import com.wavemaker.tools.api.core.resolvers.ParameterResolver;
import com.wavemaker.tools.api.core.utils.CollectionUtil;
import com.wavemaker.tools.api.core.utils.DataTypeUtil;
import com.wavemaker.tools.api.core.utils.MethodUtils;
import com.wavemaker.tools.api.core.utils.TypeUtil;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 11/11/14
 */
public abstract class AbstractMethodParser extends AbstractFoundTypesParserContainer<Operation> implements MethodParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMethodParser.class);

    protected final Method methodToParse;

    public AbstractMethodParser(final Method methodToParse) {
        super();
        this.methodToParse = methodToParse;
    }

    @Override
    public Operation parseInternal() {
        Operation operation = new Operation();

        operation.setName(methodToParse.getName());
        operation.setMethodIdentifier(MethodUtils.getMethodUniqueIdentifierId(methodToParse));

        if (methodToParse.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation apiOperation = methodToParse.getAnnotation(ApiOperation.class);
            operation.setDescription(apiOperation.value());
            operation.setNotes(apiOperation.notes());
        }
        if (methodToParse.isAnnotationPresent(WMAccessVisibility.class)) {
            operation.setAccessSpecifier(methodToParse.getAnnotation(WMAccessVisibility.class).value());
        } else {
            operation.setAccessSpecifier(ApiParserContext.getContext().getSpecifier());
        }
        operation.setDeprecated(methodToParse.isAnnotationPresent(Deprecated.class));

        handleFrameWorkSpecific(methodToParse, operation);

        // adding api produces, consumes
        if (CollectionUtil.isBlank(operation.getConsumes())) {
            operation.setConsumes(ApiParserContext.getContext().getConsumes());
        }
        if (CollectionUtil.isBlank(operation.getProduces())) {
            operation.setProduces(ApiParserContext.getContext().getProduces());
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
                Class<?> actualType = TypeUtils.getRawType(type, null);
                if (ParserRunnerContext.getInstance().getResolversContext().isResolverExist(actualType)) { // doing with
                    ParameterResolver resolver = ParserRunnerContext.getInstance().getResolversContext().getResolver(actualType);
                    final FoundTypesWrapper<List<Parameter>> foundTypesWrapper = resolver
                            .resolveParameter(i, actualType, annotations[i], operation);
                    parameters.addAll(foundTypesWrapper.getModel());
                    addNewTypes(foundTypesWrapper.getFoundTypes());
                } else {
                    ParameterParser parser = getParameterParser(i, types[i], annotations[i]);
                    TypeInformationWrapper<Parameter> parameterInfo = parser.parse();
                    parameters.add(parameterInfo.getModel());
                    addNewTypes(parameterInfo.getTypeInformation().getFoundTypes());
                }
            }
        }

        operation.setParameters(parameters);

    }

    protected void parseReturnType(Method method, Operation operation) {
        Type genericReturnType = method.getGenericReturnType();
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(genericReturnType);
        if (DataTypeUtil.isPrimitiveType(typeInformation.getActualType())) {
            operation.setReturnType(DataTypeUtil.getPrimitiveType(typeInformation.getActualType()).getType());
        } else if (typeInformation.isArray()) {
            operation.setReturnType(PrimitiveType.ARRAY.getType());
        } else {
            operation.setReturnType(DataTypeUtil.getUniqueClassName(method.getReturnType()));
        }
        operation.setReturnTypeArguments(DataTypeUtil.getUniqueClassNames(typeInformation.getTypeArguments()));
        addNewTypes(typeInformation.getFoundTypes());
    }


    protected abstract void handleFrameWorkSpecific(Method methodToParse, Operation operation);

    protected abstract ParameterParser getParameterParser(int index, Type type, Annotation[] annotations);

}
