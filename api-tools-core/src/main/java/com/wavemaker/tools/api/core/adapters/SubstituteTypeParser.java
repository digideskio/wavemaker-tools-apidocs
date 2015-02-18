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
import com.wavemaker.tools.api.core.parsers.impl.SubstituteReflectionModelParser;

/**
 * It will check if given {@link Class} have any substitute {@link Class} configured. If exists it will process that
 * one.
 *
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public class SubstituteTypeParser implements TypeParser {
    @Override
    public boolean parseType(
            final Class<?> type, final Map<Class<?>, Model> modelMap, final Collection<Class<?>> newTypes) {

        if (ParserRunnerContext.getInstance().getTypesAdapters().isSubstituteExists(type)) {
            ModelParser parser = new SubstituteReflectionModelParser(
                    ParserRunnerContext.getInstance().getTypesAdapters().getSubstitute(type)
                    , type);
            final FoundTypesWrapper<Model> foundTypesWrapper = parser.parse();
            modelMap.put(type, foundTypesWrapper.getModel());
            newTypes.addAll(foundTypesWrapper.getFoundTypes());
            return true;
        }

        return false;
    }
}
