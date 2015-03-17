/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.resolver;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import com.wavemaker.tools.apidocs.tools.core.model.FoundTypesWrapper;
import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.Parameter;
import com.wavemaker.tools.apidocs.tools.parser.resolver.ParameterResolver;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class ServletMetaTypesResolver implements ParameterResolver {

    private ServletMetaTypesResolver() {
    }

    private static class ServletMetaTypesResolverHolder {
        private static final ServletMetaTypesResolver INSTANCE = new ServletMetaTypesResolver();
    }

    public static ServletMetaTypesResolver getInstance() {
        return ServletMetaTypesResolverHolder.INSTANCE;
    }

    @Override
    public FoundTypesWrapper<List<Parameter>> resolveParameter(
            final int index, final Class<?> type, final Annotation[] annotations,
            final Operation operation) {
        return new FoundTypesWrapper<List<Parameter>>(Collections.EMPTY_LIST); // no need to fetch parameter info.
    }
}
