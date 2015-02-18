/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.config;

import java.util.Set;

import com.wavemaker.tools.api.core.filters.ModelFilter;
import com.wavemaker.tools.api.core.utils.CollectionUtil;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 1/12/14
 */
public class ModelFilterConfig {
    private final Set<ModelFilter> modelFilterSet;

    public ModelFilterConfig(final Set<ModelFilter> modelFilterSet) {
        this.modelFilterSet = modelFilterSet;
    }

    public Set<ModelFilter> getModelFilterSet() {
        return modelFilterSet;
    }

    /**
     * @param type to be filtered.
     * @return true if given {@link Class} is applied by any one of configured {@link ModelFilter}, else false.
     */
    public boolean applyFilters(Class<?> type) {
        if (CollectionUtil.isNotBlank(modelFilterSet)) {
            for (final ModelFilter modelFilter : modelFilterSet) {
                if (modelFilter.apply(type)) {
                    return true;
                }
            }
        }
        return false;
    }
}
