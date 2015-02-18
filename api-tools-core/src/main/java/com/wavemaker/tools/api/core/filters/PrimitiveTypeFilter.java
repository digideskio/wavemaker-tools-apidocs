/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.filters;

import com.wavemaker.tools.api.core.utils.DataTypeUtil;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 1/12/14
 */
public class PrimitiveTypeFilter implements ModelFilter {
    @Override
    public boolean apply(final Class<?> input) {

        // in collection type usually have 'null' entry.
        return (input == null || DataTypeUtil.isPrimitiveType(input));
    }
}
