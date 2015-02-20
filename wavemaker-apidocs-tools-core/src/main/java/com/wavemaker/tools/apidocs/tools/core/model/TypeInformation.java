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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 14/11/14
 */
public class TypeInformation {
    private final Class<?> actualType;
    private final Set<Class<?>> typeArguments;
    private final Set<Class<?>> foundTypes;
    private final boolean isArray;

    public TypeInformation(
            final Class<?> actualType, final Set<Class<?>> typeArguments, final Set<Class<?>> foundTypes,
            final boolean isArray) {
        this.actualType = actualType;
        this.typeArguments = typeArguments;
        this.foundTypes = foundTypes;
        this.isArray = isArray;
    }

    public TypeInformation(final Class<?> actualType) {
        this(actualType, new HashSet<Class<?>>(0), new HashSet<Class<?>>(0), actualType.isArray());
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
     * @return {@link Set} of type arguments.
     */
    public Set<Class<?>> getTypeArguments() {
        return typeArguments;
    }

    /**
     * It should returns the actual type of the given Type. If given type is a {@link ParameterizedType}, it will
     * returns {@link ParameterizedType#getRawType()}.
     *
     * @return Actual {@link Class} type.
     */
    public Set<Class<?>> getFoundTypes() {
        return foundTypes;
    }

    public boolean isArray() {
        return isArray;
    }

    @Override
    public String toString() {
        return "TypeInformation{" +
                "actualType=" + actualType +
                ", typeArguments=" + typeArguments +
                ", foundTypes=" + foundTypes +
                ", isArray=" + isArray +
                '}';
    }
}
