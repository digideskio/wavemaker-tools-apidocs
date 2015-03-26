/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;
import com.wavemaker.tools.apidocs.tools.core.model.Info;
import com.wavemaker.tools.apidocs.tools.core.model.Scheme;
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
public class SwaggerConfiguration {

    private ClassLoader classLoader;
    private ClassScanner classScanner;
    private String baseUrl;
    private ParameterResolvers parameterResolvers;
    private TypeAdapters typeAdapters;
    private ModelFilters modelFilters;
    private String collectionFormat;
    private Set<Scheme> schemes;
    private Info info;
    private boolean editable;
    private int coreThreadPoolSize;
    private int maxThreadPoolSize;
    private long timeout;


    private SwaggerConfiguration(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.parameterResolvers = builder.parameterResolvers;
        this.typeAdapters = builder.typeAdapters;
        this.modelFilters = builder.modelFilters;
        this.collectionFormat = builder.collectionFormat;
        this.schemes = builder.schemes;
        this.editable = builder.editable;
        this.info = builder.info;
        this.coreThreadPoolSize = builder.coreThreadPoolSize;
        this.maxThreadPoolSize = builder.maxThreadPoolSize;
        this.timeout = builder.timeout;
        this.classLoader = Objects.requireNonNull(builder.classLoader, "Class loader should not be null");
        this.classScanner = Objects.requireNonNull(builder.classScanner, "Class scanner should not be null");
    }

    public static class Builder {
        private static final int CORE_THREAD_POOL_SIZE = 4;
        private static final int MAX_THREAD_POOL_SIZE = 6;

        private String baseUrl = "";
        private boolean editable = true;
        private boolean excludeSubPackages = true;
        private int coreThreadPoolSize = 1; // default thread executor with '1'.
        private int maxThreadPoolSize = 1;
        private long timeout = 30 * 1000; // milliseconds
        private String collectionFormat = CollectionFormat.MULTI.name().toLowerCase();
        private Set<Scheme> schemes = Sets.newHashSet(Arrays.asList(Scheme.HTTP, Scheme.HTTPS));
        private Info info;

        private ClassScanner classScanner;
        private ClassLoader classLoader;
        private ParameterResolvers parameterResolvers;
        private TypeAdapters typeAdapters;
        private Set<String> excludeModelPackages;
        private ModelFilters modelFilters;
        private Set<ModelFilter> customModelFilters;

        public Builder(String baseUrl, ClassScanner scanner) {
            this.baseUrl = baseUrl;
            this.classScanner = Objects.requireNonNull(scanner, "Scanner should not be null");

            this.parameterResolvers = new ParameterResolvers();
            this.typeAdapters = new TypeAdapters();
            this.modelFilters = new ModelFilters();
            this.excludeModelPackages = new HashSet<>();
            this.customModelFilters = new LinkedHashSet<>();
        }

        public Builder setClassLoader(final ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        public Builder addParameterResolver(final Class<?> type, ParameterResolver resolver) {
            this.parameterResolvers.addResolver(type, resolver);
            return this;
        }

        public Builder addTypeSubstitute(final Class<?> actualType, final Class<?> substituteType) {
            this.typeAdapters.addSubstitute(actualType, substituteType);
            return this;
        }

        public Builder addModelValidator(final ModelFilter modelFilter) {
            customModelFilters.add(modelFilter);
            return this;
        }

        public Builder addExcludeModelPackage(final String packageName) {
            this.excludeModelPackages.add(packageName);
            return this;
        }

        public Builder setExcludeSubPackages(final boolean excludeSubPackages) {
            this.excludeSubPackages = excludeSubPackages;
            return this;
        }

        public Builder setBaseUrl(final String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setEditable(final boolean editable) {
            this.editable = editable;
            return this;
        }

        public Builder setInfo(final Info info) {
            this.info = info;
            return this;
        }

        public Builder setDefaultThreadPool() {
            this.setCoreThreadPoolSize(CORE_THREAD_POOL_SIZE);
            this.setMaxThreadPoolSize(MAX_THREAD_POOL_SIZE);
            return this;
        }

        public Builder setCoreThreadPoolSize(final int coreThreadPoolSize) {
            if (coreThreadPoolSize < 1) {
                throw new IllegalArgumentException("Thread pool size cannot be less than 1");
            }
            this.coreThreadPoolSize = coreThreadPoolSize;
            return this;
        }

        public Builder setMaxThreadPoolSize(final int maxThreadPoolSize) {
            if (maxThreadPoolSize < 1) {
                throw new IllegalArgumentException("Thread pool size cannot be less than 1");
            }
            this.maxThreadPoolSize = maxThreadPoolSize;
            return this;
        }

        public Builder setTimeout(final long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder setCollectionFormat(final CollectionFormat collectionFormat) {
            if (collectionFormat != null) {
                this.collectionFormat = collectionFormat.name().toLowerCase();
            }
            return this;
        }

        public Builder setSchemes(final Scheme... schemes) {
            this.schemes = Sets.newHashSet(Arrays.asList(schemes));
            return this;
        }

        public SwaggerConfiguration build() {
            if (classLoader == null) {
                this.classLoader = this.getClass().getClassLoader();
            }

            Set<ModelFilter> modelFiltersSet = new LinkedHashSet<>();
            modelFiltersSet.add(new PrimitiveTypeFilter());
            modelFiltersSet.add(new ObjectTypeFilter());
            if (CollectionUtil.isNotBlank(excludeModelPackages)) {
                modelFiltersSet.add(new ModelPackageFilter(excludeModelPackages, excludeSubPackages));
            }
            modelFiltersSet.addAll(customModelFilters);
            this.modelFilters = new ModelFilters(modelFiltersSet);
            return new SwaggerConfiguration(this);
        }
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

    public ParameterResolvers getParameterResolvers() {
        return parameterResolvers;
    }

    public TypeAdapters getTypeAdapters() {
        return typeAdapters;
    }

    public ModelFilters getModelFilters() {
        return modelFilters;
    }

    public String getCollectionFormat() {
        return collectionFormat;
    }

    public Info getInfo() {
        return info;
    }

    public boolean isEditable() {
        return editable;
    }

    public int getCoreThreadPoolSize() {
        return coreThreadPoolSize;
    }

    public int getMaxThreadPoolSize() {
        return maxThreadPoolSize;
    }

    public Set<Scheme> getSchemes() {
        return schemes;
    }

    public long getTimeout() {
        return timeout;
    }
}
