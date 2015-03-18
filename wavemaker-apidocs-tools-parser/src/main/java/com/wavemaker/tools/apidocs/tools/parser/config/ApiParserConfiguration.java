/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.config;

import java.util.Objects;

import com.wavemaker.tools.apidocs.tools.parser.context.ParameterResolvers;
import com.wavemaker.tools.apidocs.tools.parser.scanner.ClassScanner;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class ApiParserConfiguration {

    ClassLoader classLoader;
    ClassScanner classScanner;
    String baseUrl;
    ParameterResolvers resolversContext;
    TypeAdaptersConfig typeAdaptersConfig;
    ModelFilterConfig modelFilterConfig;
    String collectionFormat;
    boolean isEditable;
    int coreThreadPoolSize;
    int maxThreadPoolSize;


    public ApiParserConfiguration(
            final ClassLoader classLoader, final ClassScanner classScanner,
            final String baseUrl, final ParameterResolvers resolversContext,
            final TypeAdaptersConfig typeAdaptersConfig, final ModelFilterConfig modelFilterConfig,
            final String collectionFormat, final boolean isEditable, final int coreThreadPoolSize,
            final int maxThreadPoolSize) {
        this.baseUrl = baseUrl;
        this.resolversContext = resolversContext;
        this.typeAdaptersConfig = typeAdaptersConfig;
        this.modelFilterConfig = modelFilterConfig;
        this.collectionFormat = collectionFormat;
        this.isEditable = isEditable;
        this.coreThreadPoolSize = coreThreadPoolSize;
        this.maxThreadPoolSize = maxThreadPoolSize;
        this.classLoader = Objects.requireNonNull(classLoader, "Class loader should not be null");
        this.classScanner = Objects.requireNonNull(classScanner, "Class scanner should not be null");
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public ClassScanner getClassScanner() {
        return classScanner;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public ParameterResolvers getResolversContext() {
        return resolversContext;
    }

    public TypeAdaptersConfig getTypeAdaptersConfig() {
        return typeAdaptersConfig;
    }

    public ModelFilterConfig getModelFilterConfig() {
        return modelFilterConfig;
    }

    public String getCollectionFormat() {
        return collectionFormat;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public int getCoreThreadPoolSize() {
        return coreThreadPoolSize;
    }

    public int getMaxThreadPoolSize() {
        return maxThreadPoolSize;
    }
}
