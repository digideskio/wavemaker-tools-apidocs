/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tools.apidocs.tools.parser.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
        return new TypeInformation(actualType, Collections.EMPTY_LIST, false, type);
    }

    protected static TypeInformation getParameterizedTypeTypeInformation(ParameterizedType parameterizedType) {
        List<Class<?>> typeArguments = new LinkedList<>();
        Class<?> actualType = (Class<?>) parameterizedType.getRawType();

        for (Type type : parameterizedType.getActualTypeArguments()) {
            TypeInformation typeInfo = extractTypeInformation(type);
            typeArguments.add(typeInfo.getActualType());
        }
        return new TypeInformation(actualType, typeArguments, false, parameterizedType);
    }

    protected static TypeInformation getArrayTypeInformation(Type type) {
        Class<?> actualType;
        List<Class<?>> typeArguments = new LinkedList<>();
        if (type instanceof GenericArrayType) { // for cases like T[]
            Type rawType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> rawComponentType = extractTypeInformation(rawType).getActualType();
            actualType = Array.newInstance(rawComponentType, 0).getClass(); // instantiating array type
            typeArguments.add(rawComponentType);
        } else {
            actualType = TypeUtils.getRawType(type, null);
            typeArguments.add((Class<?>) TypeUtils.getArrayComponentType(type));// check type.
        }
        return new TypeInformation(actualType, typeArguments, true, type);
    }
}
