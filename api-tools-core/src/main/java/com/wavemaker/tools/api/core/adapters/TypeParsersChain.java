/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.adapters;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wavemaker.tools.api.core.context.ParserRunnerContext;
import com.wavemaker.tools.api.core.models.Model;
import com.wavemaker.tools.api.core.utils.CollectionUtil;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public class TypeParsersChain {
    private final List<TypeParser> typeParsers;
    private final Map<Class<?>, Model> modelMap;
    private final Set<Class<?>> processedTypes;

    public TypeParsersChain(final List<TypeParser> typeParsers) {
        this.typeParsers = typeParsers;
        modelMap = new LinkedHashMap<>();
        processedTypes = new HashSet<>();
    }

    public void processTypes(Collection<Class<?>> types) {
        if (CollectionUtil.isNotBlank(types)) {
            for (final Class<?> type : types) {
                processType(type);
            }
        }

    }

    /**
     * It will process the given type recursively.
     *
     * @param type to be parse
     */
    public void processType(Class<?> type) {
        if (!processedTypes.contains(type)) {
            processedTypes.add(type); // adding entry.
            if (!ParserRunnerContext.getInstance().getModelFilterConfig().applyFilters(type)) {
                Collection<Class<?>> newTypes = new LinkedHashSet<>();
                for (final TypeParser typeParser : typeParsers) {
                    if (typeParser.parseType(type, modelMap, newTypes)) {
                        break; // if any processor accepts, will break over there.
                    }
                }
                // processing new types.
                processTypes(newTypes);
            }
        }
    }

    public Map<Class<?>, Model> getModelMap() {
        return modelMap;
    }
}
