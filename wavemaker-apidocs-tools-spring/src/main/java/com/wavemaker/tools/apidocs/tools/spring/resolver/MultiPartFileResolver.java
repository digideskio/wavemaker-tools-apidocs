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

import com.google.common.collect.Lists;
import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.AbstractParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.BodyParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.QueryParameter;
import com.wavemaker.tools.apidocs.tools.core.model.properties.FileProperty;
import com.wavemaker.tools.apidocs.tools.core.utils.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.parser.resolver.ParameterResolver;
import com.wavemaker.tools.apidocs.tools.parser.util.DataTypeUtil;
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
    public List<Parameter> resolveParameter(
            final int index, final Class<?> type, final Annotation[] annotations,
            final Operation operation) {
        SpringParameterParser parameterParser = new SpringParameterParser(index, type, annotations);
        Parameter parameter = parameterParser.parse();

        if (!(parameter instanceof BodyParameter)) {
            FileProperty property = new FileProperty();
            property.setRequired(true);
            if (parameter instanceof FormParameter) {
                ((FormParameter) parameter).property(property);
            } else if (parameter instanceof QueryParameter) {
                ((QueryParameter) parameter).property(property);
            }
        }

        ((AbstractParameter) parameter).setResolver(DataTypeUtil.getName(type));
        // setting consumes to multi part form
        operation.setConsumes(Lists.newArrayList(MediaType.MULTIPART_FORM_DATA_VALUE));

        return CollectionUtil.asList(parameter);
    }
}
