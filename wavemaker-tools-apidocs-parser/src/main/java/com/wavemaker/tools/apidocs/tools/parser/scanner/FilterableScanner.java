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

import java.util.List;
import java.util.Objects;

import org.reflections.util.FilterBuilder;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.wavemaker.tools.apidocs.tools.core.utils.CollectionUtil;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 11/6/15
 */
public abstract class FilterableScanner extends FilterBuilder {

    public static final String CLASS_EXT = ".class";

    public FilterableScanner includeAll() {
        this.include(".*");
        return this;
    }

    public FilterableScanner excludeAll() {
        this.exclude(".*");
        return this;
    }

    public FilterableScanner includePackages(List<String> packages) {
        if (CollectionUtil.isNotEmpty(packages)) {
            for (final String aPackage : packages) {
                includePackage(aPackage);
            }
        }
        return this;
    }

    public FilterableScanner excludePackages(List<String> packages) {
        if (CollectionUtil.isNotEmpty(packages)) {
            for (final String aPackage : packages) {
                excludePackage(aPackage);
            }
        }
        return this;
    }

    @Override
    public FilterableScanner includePackage(final String... prefixes) {
        for (String prefix : prefixes) {
            add(new Include(prefix(asPackagePrefix(prefix))));
        }
        return this;
    }

    @Override
    public FilterableScanner excludePackage(final String prefix) {
        super.excludePackage(asPackagePrefix(prefix));
        return this;
    }

    public FilterableScanner excludeType(Class type) {
        Objects.requireNonNull(type, "Exclude type cannot be null");
        add(Predicates.not(getEqualClassPredicate(type.getName())));
        return this;
    }

    public FilterableScanner includeType(Class type) {
        Objects.requireNonNull(type, "Include type cannot be null");
        add(getEqualClassPredicate(type.getName()));
        return this;
    }

    public FilterableScanner includeTypes(List<String> types) {
        if (CollectionUtil.isNotEmpty(types)) {
            for (final String type : types) {
                add(getEqualClassPredicate(type));
            }
        }
        return this;
    }

    public FilterableScanner excludeTypes(List<String> types) {
        if (CollectionUtil.isNotEmpty(types)) {
            for (final String type : types) {
                add(Predicates.not(getEqualClassPredicate(type)));
            }
        }
        return this;
    }

    private Predicate<String> getEqualClassPredicate(final String type) {
        return Predicates.equalTo(type + CLASS_EXT);
    }

    private String asPackagePrefix(String prefix) {
        return prefix.endsWith(".") ? prefix : (prefix + ".");
    }
}
