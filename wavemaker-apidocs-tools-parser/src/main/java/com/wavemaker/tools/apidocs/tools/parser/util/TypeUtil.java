/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.reflect.TypeUtils;

import com.wavemaker.tools.apidocs.tools.core.model.TypeInformation;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public class TypeUtil {

    /**
     * It will scan for given {@link Type}.
     * <p/>
     * For eg: Type is <code>List&ltString&gt</code>. It will Returns: Actual Type: List Type Arguments: String
     *
     * @param type type to scan
     * @return {@link TypeInformation} of given type.
     */
    public static TypeInformation extractTypeInformation(Type type) {
        Class<?> actualType;
        if (TypeUtils.isArrayType(type)) { // conditions order is important.
            return getArrayTypeInformation(type);
        } else if (type instanceof Class<?>) {
            actualType = (Class<?>) type; // enums take strings only.
        } else if (type instanceof ParameterizedType) {
            return getParameterizedTypeTypeInformation((ParameterizedType) type);
        } else { // cases like WildCard Type and TypeVariable
            actualType = Object.class; // sending null doesn't make sense
        }
        return new TypeInformation(actualType, Collections.EMPTY_SET, false);
    }

    protected static TypeInformation getParameterizedTypeTypeInformation(ParameterizedType parameterizedType) {
        Set<Class<?>> typeArguments = new LinkedHashSet<>();
        Class<?> actualType = (Class<?>) parameterizedType.getRawType();

        for (Type type : parameterizedType.getActualTypeArguments()) {
            TypeInformation typeInfo = extractTypeInformation(type);
            typeArguments.add(typeInfo.getActualType());
        }
        return new TypeInformation(actualType, typeArguments, false);
    }

    protected static TypeInformation getArrayTypeInformation(Type type) {
        Class<?> actualType;
        Set<Class<?>> typeArguments = new HashSet<>();
        if (type instanceof GenericArrayType) { // for cases like T[]
            Type rawType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> rawComponentType = extractTypeInformation(rawType).getActualType();
            actualType = Array.newInstance(rawComponentType, 0).getClass(); // instantiating array type
            typeArguments.add(rawComponentType);
        } else {
            actualType = TypeUtils.getRawType(type, null);
            typeArguments.add((Class<?>) TypeUtils.getArrayComponentType(type));// check type.
        }
        return new TypeInformation(actualType, typeArguments, true);
    }
}
