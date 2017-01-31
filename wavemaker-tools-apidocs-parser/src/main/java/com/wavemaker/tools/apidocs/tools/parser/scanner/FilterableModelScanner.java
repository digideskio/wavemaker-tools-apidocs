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
package com.wavemaker.tools.apidocs.tools.parser.scanner;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Optional;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.parser.impl.ExcludedTypeModelParser;
import com.wavemaker.tools.apidocs.tools.parser.impl.ReflectionModelParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.ModelParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 31/3/15
 */
public class FilterableModelScanner extends FilterableScanner implements ModelScanner {

    private static final ExcludedTypeModelParser excludedTypeModelParser = new ExcludedTypeModelParser();

    private Set<SubstituteInfo> substitutes;
    private Set<AdapterInfo> adapters;
    private Set<PackageAdapterInfo> packageAdapters;

    public FilterableModelScanner() {
        substitutes = new LinkedHashSet<>();
        adapters = new LinkedHashSet<>();
        packageAdapters = new HashSet<>();
//        excludeType(Object.class); // excluding object model by default
    }

    @Override
    public void addPackageTypeAdapter(final String packagePrefix, final ModelParser parser) {
        packageAdapters.add(new PackageAdapterInfo(packagePrefix, parser));
    }

    @Override
    public void addTypeAdapter(final Class<?> type, final ModelParser parser, final boolean includeSubTypes) {
        adapters.add(new AdapterInfo(type, parser, includeSubTypes));
    }

    @Override
    public void addSubstitute(final Class<?> baseType, final Class<?> substituteType, final boolean includeSubTypes) {
        substitutes.add(new SubstituteInfo(baseType, substituteType, includeSubTypes));
    }

    @Override
    public FilterableModelScanner excludePackage(final String prefix) {
        addPackageTypeAdapter(prefix, excludedTypeModelParser);
        return this;
    }

    @Override
    public FilterableModelScanner excludeType(final Class type) {
        addTypeAdapter(type, excludedTypeModelParser, false);
        return this;
    }

    @Override
    public boolean filter(final Class<?> type) {
        Objects.requireNonNull(type, "Type cannot be null");
        return !ClassUtils.isPrimitiveOrWrapper(type) && apply(type.getName() + CLASS_EXT);
    }

    @Override
    public Optional<Model> scanModel(final Class<?> type) {
        if (filter(type)) {
            ModelParser parser;
            Optional<ModelParser> parserOptional = findParser(type);
            if (parserOptional.isPresent()) {
                parser = parserOptional.get();
            } else {
                parser = new ReflectionModelParser(type); // parsing with default
            }
            return Optional.of(parser.parse());
        }
        return Optional.absent();
    }

    protected Optional<ModelParser> findParser(Class<?> type) {
        Class<?> finalType = type;
        for (final SubstituteInfo substitute : substitutes) {
            if (substitute.canApply(type)) {
                finalType = substitute.substituteType;
                break;
            }
        }

        for (final AdapterInfo adapter : adapters) {
            if (adapter.canApply(finalType)) {
                return Optional.of(adapter.parser);
            }
        }

        for (final PackageAdapterInfo packageAdapter : packageAdapters) {
            if (packageAdapter.canApply(finalType)) {
                return Optional.of(packageAdapter.parser);
            }
        }

        return Optional.absent();
    }

    private static class Adapter {
        private Class<?> type;
        private boolean includeSubTypes;

        public Adapter(final Class<?> type, final boolean includeSubTypes) {
            this.type = Objects.requireNonNull(type, "Adapter/Substitute type cannot be null");
            this.includeSubTypes = includeSubTypes;
        }

        public boolean canApply(Class<?> targetType) {
            if (targetType != null) {
                if (type.equals(targetType)) {
                    return true;
                } else if (includeSubTypes) {
                    return type.isAssignableFrom(targetType);
                }
            }
            return false;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof Adapter)) return false;

            final Adapter that = (Adapter) o;

            if (includeSubTypes != that.includeSubTypes) return false;
            if (!type.equals(that.type)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = 31 * result + (includeSubTypes ? 1 : 0);
            return result;
        }
    }

    private static class AdapterInfo extends Adapter {
        private ModelParser parser;

        public AdapterInfo(final Class<?> type, final ModelParser parser, final boolean includeSubTypes) {
            super(type, includeSubTypes);
            this.parser = Objects.requireNonNull(parser, "Model Parser type cannot be null");
        }
    }

    private static class SubstituteInfo extends Adapter {
        private Class<?> substituteType;

        public SubstituteInfo(final Class<?> baseType, final Class<?> substituteType, final boolean includeSubTypes) {
            super(baseType, includeSubTypes);
            this.substituteType = Objects.requireNonNull(substituteType, "Substitute type cannot be null");
        }
    }

    private static class PackageAdapterInfo {

        private String packagePrefix;
        private ModelParser parser;

        public PackageAdapterInfo(final String packagePrefix, final ModelParser parser) {
            if (StringUtils.isNotBlank(packagePrefix)) {
                this.packagePrefix = packagePrefix;
            } else {
                throw new IllegalArgumentException("Package prefix cannot be null or empty");
            }
            this.parser = Objects.requireNonNull(parser, "Model Parser type cannot be null");
        }

        public boolean canApply(Class<?> targetType) {
            if (targetType != null) {
                if (targetType.getName().startsWith(packagePrefix)) {
                    return true;
                }
            }
            return false;
        }

        public ModelParser getParser() {
            return parser;
        }
    }
}
