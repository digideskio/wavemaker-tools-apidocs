/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.config;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.wavemaker.tools.apidocs.tools.core.utils.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.parser.filter.ModelFilter;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 1/12/14
 */
public class ModelFilters implements Predicate<Class<?>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelFilters.class);

    private final Set<ModelFilter> modelFilterSet;

    public ModelFilters(final Set<ModelFilter> modelFilterSet) {
        this.modelFilterSet = modelFilterSet;
    }

    public ModelFilters() {
        this(new LinkedHashSet<ModelFilter>());
    }

    public void addFilter(ModelFilter filter) {
        this.modelFilterSet.add(filter);
    }

    public List<Class<?>> filter(List<Class<?>> types) {
        List<Class<?>> filteredTypes = new LinkedList<>();
        if (CollectionUtil.isNotBlank(types)) {
            for (final Class<?> type : types) {
                if (apply(type)) {
                    filteredTypes.add(type);
                }
            }
        }
        return filteredTypes;
    }

    /**
     * @param type to be filtered.
     * @return true if given {@link Class} validated by {@link ModelFilter}'s else false.
     */
    @Override
    public boolean apply(Class<?> type) {
        if (CollectionUtil.isNotBlank(modelFilterSet)) {
            for (final ModelFilter modelFilter : modelFilterSet) {
                if (!modelFilter.evaluate(type)) {
                    LOGGER.info("Model Validator:{}, rejected type:{}", modelFilter, type);
                    return false;
                }
            }
        }
        return true;
    }
}
