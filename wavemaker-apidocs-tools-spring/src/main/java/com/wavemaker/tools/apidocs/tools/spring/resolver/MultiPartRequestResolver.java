/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.resolver;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.MediaType;

import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.parser.resolver.ParameterResolver;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 18/11/14
 */
public class MultiPartRequestResolver implements ParameterResolver {

    public MultiPartRequestResolver() {
    }

    @Override
    public List<Parameter> resolveParameter(
            final int index, final Class<?> type, final Annotation[] annotations,
            final Operation operation) {
        // setting consumes
        operation.setConsumes(Arrays.asList(MediaType.MULTIPART_FORM_DATA_VALUE));
        return Collections.EMPTY_LIST;
    }
}
