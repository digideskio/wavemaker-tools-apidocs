/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.config;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.wavemaker.tools.apidocs.tools.core.utils.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.parser.context.ParameterResolvers;
import com.wavemaker.tools.apidocs.tools.parser.filter.ModelFilter;
import com.wavemaker.tools.apidocs.tools.parser.filter.ModelPackageFilter;
import com.wavemaker.tools.apidocs.tools.parser.filter.ObjectTypeFilter;
import com.wavemaker.tools.apidocs.tools.parser.filter.PrimitiveTypeFilter;
import com.wavemaker.tools.apidocs.tools.parser.resolver.ParameterResolver;
import com.wavemaker.tools.apidocs.tools.parser.scanner.ClassScanner;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class ApiParserConfigurationBuilder {
    private static final int CORE_THREAD_POOL_SIZE = 4;
    private static final int MAX_THREAD_POOL_SIZE = 6;

    private ClassLoader classLoader;
    private ClassScanner classScanner;
    private String baseUrl;
    private boolean excludeSubPackages = true;
    private boolean isApisEditable = true;

    private final ParameterResolvers resolversContext = new ParameterResolvers();
    private final TypeAdaptersConfig typeAdaptersConfig = new TypeAdaptersConfig();
    private final Set<String> excludeModelPackages = new HashSet<>();
    private final Set<ModelFilter> modelFilters = new LinkedHashSet<>();
    private int coreThreadPoolSize = 1; // default thread executor with '1'.
    private int maxThreadPoolSize = 1;
    private String collectionFormat;

    public ApiParserConfigurationBuilder setClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    public ApiParserConfigurationBuilder setClassScanner(final ClassScanner classScanner) {
        this.classScanner = classScanner;
        return this;
    }

    public ApiParserConfigurationBuilder addParameterResolver(final Class<?> type, ParameterResolver resolver) {
        this.resolversContext.addResolver(type, resolver);
        return this;
    }

    public ApiParserConfigurationBuilder addTypeSubstitute(final Class<?> actualType, final Class<?> substituteType) {
        this.typeAdaptersConfig.addSubstitute(actualType, substituteType);
        return this;
    }

    public ApiParserConfigurationBuilder addExcludeModelPackage(final String packageName) {
        this.excludeModelPackages.add(packageName);
        return this;
    }

    public ApiParserConfigurationBuilder setExcludeSubPackages(final boolean excludeSubPackages) {
        this.excludeSubPackages = excludeSubPackages;
        return this;
    }

    public ApiParserConfigurationBuilder addModelFilter(final ModelFilter modelFilter) {
        modelFilters.add(modelFilter);
        return this;
    }

    public ApiParserConfigurationBuilder setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public ApiParserConfigurationBuilder setApisEditable(final boolean isApisEditable) {
        this.isApisEditable = isApisEditable;
        return this;
    }

    public ApiParserConfigurationBuilder setDefaultThreadPool() {
        this.setCoreThreadPoolSize(CORE_THREAD_POOL_SIZE);
        this.setMaxThreadPoolSize(MAX_THREAD_POOL_SIZE);
        return this;
    }

    public ApiParserConfigurationBuilder setCoreThreadPoolSize(final int coreThreadPoolSize) {
        if (coreThreadPoolSize < 1) {
            throw new IllegalArgumentException("Thread pool size cannot be less than 1");
        }
        this.coreThreadPoolSize = coreThreadPoolSize;
        return this;
    }

    public ApiParserConfigurationBuilder setMaxThreadPoolSize(final int maxThreadPoolSize) {
        if (maxThreadPoolSize < 1) {
            throw new IllegalArgumentException("Thread pool size cannot be less than 1");
        }
        this.maxThreadPoolSize = maxThreadPoolSize;
        return this;
    }

    public void setCollectionFormat(final String collectionFormat) {
        this.collectionFormat = collectionFormat;
    }

    public ApiParserConfiguration build() {
        if (classLoader == null) {
            this.classLoader = this.getClass().getClassLoader();
        }

        Set<ModelFilter> modelFiltersSet = new LinkedHashSet<>();
        modelFiltersSet.add(new PrimitiveTypeFilter());
        modelFiltersSet.add(new ObjectTypeFilter());
        if (CollectionUtil.isNotBlank(excludeModelPackages)) {
            modelFiltersSet.add(new ModelPackageFilter(excludeModelPackages, excludeSubPackages));
        }
        modelFiltersSet.addAll(modelFilters);
        return new ApiParserConfiguration(classLoader, classScanner, baseUrl, resolversContext, typeAdaptersConfig,
                new ModelFilterConfig(modelFiltersSet), collectionFormat, isApisEditable, coreThreadPoolSize,
                maxThreadPoolSize);
    }
}
