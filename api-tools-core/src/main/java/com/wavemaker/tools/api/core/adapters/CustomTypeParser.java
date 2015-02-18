/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.adapters;

import java.util.Collection;
import java.util.Map;

import com.wavemaker.tools.api.core.context.ParserRunnerContext;
import com.wavemaker.tools.api.core.models.FoundTypesWrapper;
import com.wavemaker.tools.api.core.models.Model;
import com.wavemaker.tools.api.core.parsers.ModelParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public class CustomTypeParser implements TypeParser {
    @Override
    public boolean parseType(
            final Class<?> type, final Map<Class<?>, Model> modelMap, final Collection<Class<?>> newTypes) {
        if (ParserRunnerContext.getInstance().getTypesAdapters().isTypeHaveAdapter(type)) {
            ModelParser parser = ParserRunnerContext.getInstance().getTypesAdapters().getTypeAdapter(type);

            final FoundTypesWrapper<Model> foundTypesWrapper = parser.parse();
            if (foundTypesWrapper != null) {
                modelMap.put(type, foundTypesWrapper.getModel());
                newTypes.addAll(foundTypesWrapper.getFoundTypes());
            }

            return true;
        }

        return false;
    }
}
