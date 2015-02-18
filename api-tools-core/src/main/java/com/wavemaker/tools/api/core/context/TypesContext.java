/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.context;

import java.util.LinkedHashMap;
import java.util.Map;

import com.wavemaker.tools.api.core.utils.DataTypeUtil;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class TypesContext {

    private final Map<Class<?>, String> typesMap;
    private final Map<String, Class<?>> reverseTypesMap;


    TypesContext() {
        reverseTypesMap = new LinkedHashMap<>();
        typesMap = new LinkedHashMap<>();
    }

    protected void addType(Class<?> type, String name) {
        typesMap.put(type, name);
        reverseTypesMap.put(name, type);
    }

    /**
     * It will give you a unique name for each type.
     * <p/>
     * For example if User class defined in two packages. com.wavemaker.models.user com.wavemaker.entities.user
     * <p/>
     * When you call first type it will give you User, and for next type it will give entities.User
     *
     * @param type target class.
     * @return Unique name in the model context.
     */
    public String getUniqueTypeName(Class<?> type) {
        if (typesMap.containsKey(type)) { // type already cached.
            return typesMap.get(type);
        }
        // trying to create new type name.
        String uniqueName = findUniqueNameRecursively(type, 1);
        addType(type, uniqueName);

        return uniqueName;
    }

    private String findUniqueNameRecursively(Class<?> type, int attempt) {
        String name = DataTypeUtil.getName(type, attempt);
        if (reverseTypesMap.get(name) == null) {
            // not found, means its a new type.
            return name;
        } else {
            return findUniqueNameRecursively(type, ++attempt);
        }
    }

}
