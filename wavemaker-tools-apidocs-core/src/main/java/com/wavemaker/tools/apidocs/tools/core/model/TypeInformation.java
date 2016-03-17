/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
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
    private final Type genericType;
    private final Class<?> actualType;
    private final List<Class<?>> typeArguments;
    private final boolean isArray;

    public TypeInformation(
            final Class<?> actualType, final List<Class<?>> typeArguments, final boolean isArray, final Type genericType) {
        this.actualType = actualType;
        this.typeArguments = typeArguments;
        this.isArray = isArray;
        this.genericType = genericType;
    }

    public TypeInformation(final Class<?> actualType, final Type genericType) {
        this(actualType, new ArrayList<Class<?>>(0), actualType.isArray(), genericType);
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

    public Type getGenericType() {
        return genericType;
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
