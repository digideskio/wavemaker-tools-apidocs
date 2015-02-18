/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.spring.resolvers;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import org.springframework.http.MediaType;

import com.wavemaker.tools.api.core.models.FoundTypesWrapper;
import com.wavemaker.tools.api.core.models.Operation;
import com.wavemaker.tools.api.core.models.Parameter;
import com.wavemaker.tools.api.core.resolvers.ParameterResolver;
import com.wavemaker.tools.api.core.utils.CollectionUtil;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 18/11/14
 */
public class MultiPartRequestResolver implements ParameterResolver {

    private MultiPartRequestResolver() {
    }

    private static class MultiPartRequestResolverHolder {
        private static final MultiPartRequestResolver INSTANCE = new MultiPartRequestResolver();
    }

    public static MultiPartRequestResolver getInstance() {
        return MultiPartRequestResolverHolder.INSTANCE;
    }
    
    @Override
    public FoundTypesWrapper<List<Parameter>> resolveParameter(
            final int index, final Class<?> type, final Annotation[] annotations,
            final Operation operation) {
        // setting consumes
        operation.setConsumes(CollectionUtil.asSet(MediaType.MULTIPART_FORM_DATA_VALUE));
        return new FoundTypesWrapper<List<Parameter>>(Collections.EMPTY_LIST);
    }
}
