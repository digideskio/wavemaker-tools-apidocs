/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.context;

import java.util.HashMap;
import java.util.Map;

import com.wavemaker.tools.api.core.resolvers.ParameterResolver;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class ParameterResolversContext {
    private final Map<Class<?>, ParameterResolver> resolverMap;

    public ParameterResolversContext(final Map<Class<?>, ParameterResolver> resolverMap) {
        this.resolverMap = resolverMap;
    }

    public ParameterResolversContext() {
        this(new HashMap<Class<?>, ParameterResolver>());
    }

    public void addResolver(Class<?> type, ParameterResolver resolver) {
        resolverMap.put(type, resolver);
    }

    public ParameterResolver getResolver(Class<?> type) {
        return resolverMap.get(type);
    }

    public boolean isResolverExist(Class<?> type) {
        return resolverMap.containsKey(type);
    }


}
