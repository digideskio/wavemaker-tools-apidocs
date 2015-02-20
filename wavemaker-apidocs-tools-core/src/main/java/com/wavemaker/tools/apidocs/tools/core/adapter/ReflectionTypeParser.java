/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.adapter;

import java.util.Collection;
import java.util.Map;

import com.wavemaker.tools.apidocs.tools.core.model.FoundTypesWrapper;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.parser.ModelParser;
import com.wavemaker.tools.apidocs.tools.core.parsers.impl.ReflectionModelParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public class ReflectionTypeParser implements TypeParser {
    @Override
    public boolean parseType(
            final Class<?> type, final Map<Class<?>, Model> modelMap, final Collection<Class<?>> newTypes) {
        ModelParser parser = new ReflectionModelParser(type);
        final FoundTypesWrapper<Model> foundTypesWrapper = parser.parse();
        modelMap.put(type, foundTypesWrapper.getModel());
        newTypes.addAll(foundTypesWrapper.getFoundTypes());
        return true;
    }
}
