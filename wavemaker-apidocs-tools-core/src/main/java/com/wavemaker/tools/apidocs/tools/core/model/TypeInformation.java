/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 14/11/14
 */
public class TypeInformation {
    private final Class<?> actualType;
    private final List<Class<?>> typeArguments;
    private final boolean isArray;

    public TypeInformation(
            final Class<?> actualType, final List<Class<?>> typeArguments, final boolean isArray) {
        this.actualType = actualType;
        this.typeArguments = typeArguments;
        this.isArray = isArray;
    }

    public TypeInformation(final Class<?> actualType) {
        this(actualType, new ArrayList<Class<?>>(0), actualType.isArray());
    }

    /**
     * It should returns the actual type of the given Type. If given type is a {@link ParameterizedType}, it will
     * returns {@link ParameterizedType#getRawType()}.
     *
     * @return Actual {@link Class} type.
     */
    public Class<?> getActualType() {
        return actualType;
    }

    /**
     * It should returns the Actual type arguments for given type. Returns {@link Collections#emptySet()} if given
     * {@link Type} is not an instance of {@link ParameterizedType}.
     *
     * @return {@link List} of type arguments.
     */
    public List<Class<?>> getTypeArguments() {
        return typeArguments;
    }

    public boolean isArray() {
        return isArray;
    }

    @Override
    public String toString() {
        return "TypeInformation{" +
                "actualType=" + actualType +
                ", typeArguments=" + typeArguments +
                ", isArray=" + isArray +
                '}';
    }
}
