/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.config;

import java.util.HashMap;
import java.util.Map;

import com.wavemaker.tools.apidocs.tools.parser.parser.ModelParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 23/11/14
 */
public class TypeAdapters {

    private final Map<Class<?>, Class<?>> substituteMap;
    private final Map<Class<?>, ModelParser> typeAdapters;

    public TypeAdapters() {
        this.substituteMap = new HashMap<>();
        typeAdapters = new HashMap<>();
    }

    public void addSubstitute(Class<?> actualType, Class<?> substituteType) {
        this.substituteMap.put(actualType, substituteType);
    }

    public boolean isSubstituteExists(Class<?> actualType) {
        return substituteMap.containsKey(actualType);
    }

    public Class<?> getSubstitute(Class<?> actualType) {
        return substituteMap.get(actualType);
    }

    public void addAdapter(Class<?> type, ModelParser modelParser) {
        this.typeAdapters.put(type, modelParser);
    }

    public boolean isTypeHaveAdapter(Class<?> type) {
        return typeAdapters.containsKey(type);
    }

    public ModelParser getTypeAdapter(Class<?> type) {
        return typeAdapters.get(type);
    }
}
