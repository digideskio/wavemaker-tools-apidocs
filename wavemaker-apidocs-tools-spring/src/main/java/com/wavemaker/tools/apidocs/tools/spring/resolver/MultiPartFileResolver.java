/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
/**

 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.resolver;

import java.lang.annotation.Annotation;
import java.util.List;

import org.springframework.http.MediaType;

import com.wavemaker.tools.apidocs.tools.core.builder.PrimitiveType;
import com.wavemaker.tools.apidocs.tools.core.model.FoundTypesWrapper;
import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.TypeInformationWrapper;
import com.wavemaker.tools.apidocs.tools.core.resolver.ParameterResolver;
import com.wavemaker.tools.apidocs.tools.core.util.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.spring.parser.SpringParameterParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class MultiPartFileResolver implements ParameterResolver {

    private MultiPartFileResolver() {

    }

    private static class MultiPartFileResolverHolder {
        private static final MultiPartFileResolver INSTANCE = new MultiPartFileResolver();
    }

    public static MultiPartFileResolver getInstance() {
      return MultiPartFileResolverHolder.INSTANCE;
    }

    @Override
    public FoundTypesWrapper<List<Parameter>> resolveParameter(
            final int index, final Class<?> type, final Annotation[] annotations,
            final Operation operation) {
        SpringParameterParser parameterParser = new SpringParameterParser(index, type, annotations);
        final TypeInformationWrapper<Parameter> parameterTypeInformationWrapper = parameterParser.parse();
        Parameter parameter = parameterTypeInformationWrapper.getModel();
        parameter.setType(PrimitiveType.FILE.getType());
        parameter.setResolver(type.getName());

        // setting consumes to multi part form
        operation.setConsumes(CollectionUtil.asSet(MediaType.MULTIPART_FORM_DATA_VALUE));

        return new FoundTypesWrapper<>(CollectionUtil.asList(parameter));
    }
}
