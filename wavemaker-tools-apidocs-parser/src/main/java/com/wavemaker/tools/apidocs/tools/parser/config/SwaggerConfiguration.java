/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tools.apidocs.tools.parser.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;
import com.wavemaker.tools.apidocs.tools.core.model.Info;
import com.wavemaker.tools.apidocs.tools.core.model.Scheme;
import com.wavemaker.tools.apidocs.tools.parser.context.ParameterResolvers;
import com.wavemaker.tools.apidocs.tools.parser.impl.ExcludedTypeModelParser;
import com.wavemaker.tools.apidocs.tools.parser.resolver.ParameterResolver;
import com.wavemaker.tools.apidocs.tools.parser.scanner.ClassScanner;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableModelScanner;
import com.wavemaker.tools.apidocs.tools.parser.scanner.ModelScanner;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class SwaggerConfiguration {

    private ClassLoader classLoader;
    private ClassScanner classScanner;
    private String baseUrl;
    private ParameterResolvers parameterResolvers;
    private ModelScanner modelScanner;
    private String collectionFormat;
    private Set<Scheme> schemes;
    private Info info;
    private boolean editable;


    private SwaggerConfiguration(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.parameterResolvers = builder.parameterResolvers;
        this.modelScanner = builder.modelScanner;
        this.collectionFormat = builder.collectionFormat;
        this.schemes = builder.schemes;
        this.editable = builder.editable;
        this.info = builder.info;
        this.classLoader = Objects.requireNonNull(builder.classLoader, "Class loader should not be null");
        this.classScanner = Objects.requireNonNull(builder.classScanner, "Class scanner should not be null");
    }

    public static class Builder {
        public static final String DEFAULT_VERSION = "1.0";
        public static final String DEFAULT_TITLE = "Swagger Documentation";

        private String baseUrl = "";
        private boolean editable = true;
        private String collectionFormat = CollectionFormat.MULTI.name().toLowerCase();
        private Set<Scheme> schemes = Sets.newHashSet(Arrays.asList(Scheme.HTTP, Scheme.HTTPS));
        private Info info;

        private ClassScanner classScanner;
        private ClassLoader classLoader;
        private ParameterResolvers parameterResolvers;
        private ModelScanner modelScanner;

        public Builder(String baseUrl, ClassScanner scanner) {
            this.baseUrl = baseUrl;
            this.classScanner = Objects.requireNonNull(scanner, "Scanner should not be null");

            this.parameterResolvers = new ParameterResolvers();
        }

        public Builder setClassLoader(final ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        public Builder addParameterResolver(final Class<?> type, ParameterResolver resolver) {
            this.parameterResolvers.addResolver(type, resolver);
            return this;
        }

        public Builder setModelScanner(final ModelScanner modelScanner) {
            this.modelScanner = modelScanner;
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

        public Builder setSchemes(final Collection<Scheme> schemes) {
            this.schemes = Sets.newHashSet(schemes);
            return this;
        }

        public SwaggerConfiguration build() {
            if (classLoader == null) {
                this.classLoader = this.getClass().getClassLoader();
            }
            if (modelScanner == null) {
                modelScanner = new FilterableModelScanner();
            }
            if (StringUtils.isBlank(baseUrl)) {
                baseUrl = "/";
            }
            if (info == null) {
                info = new Info();
            }
            if (StringUtils.isBlank(info.getVersion())) {
                info.setVersion(DEFAULT_VERSION);
            }
            if (StringUtils.isBlank(info.getTitle())) {
                info.setTitle(DEFAULT_TITLE);
            }

            modelScanner.addPackageTypeAdapter("java.lang", new ExcludedTypeModelParser());

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

    public ModelScanner getModelScanner() {
        return modelScanner;
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

    public Set<Scheme> getSchemes() {
        return schemes;
    }

}
