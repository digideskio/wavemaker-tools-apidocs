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
package com.wavemaker.tools.apidocs.tools.parser.impl;

import java.io.File;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.ClassUtils;

import com.wavemaker.tools.apidocs.tools.core.model.TypeInformation;
import com.wavemaker.tools.apidocs.tools.core.model.properties.*;
import com.wavemaker.tools.apidocs.tools.core.utils.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.parser.exception.PropertyParserException;
import com.wavemaker.tools.apidocs.tools.parser.parser.PropertyParser;
import com.wavemaker.tools.apidocs.tools.parser.util.ContextUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.DataTypeUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.TypeUtil;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class PropertyParserImpl implements PropertyParser {

    protected Type type;

    protected PropertyParserImpl(Type type) {
        this.type = type;
    }

    @Override
    public Property parse() {
        return parseType(this.type);
    }

    private Property parseType(Type type) {
        Property property;
        TypeInformation typeInfo = TypeUtil.extractTypeInformation(type);

        if (!typeInfo.getTypeArguments().isEmpty()) {
            if (isArray(typeInfo)) {
                property = feedArrayProperty(typeInfo);
            } else if (Map.class.isAssignableFrom(typeInfo.getActualType())) {
                property = feedMapProperty(typeInfo);
            } else {
                property = feedObjectProperty(typeInfo);
            }
        } else {
            Class<?> actualType = typeInfo.getActualType();
            if (DataTypeUtil.isEnum(actualType) || String.class.equals(actualType)) {
                property = feedStringProperty(actualType);
            } else if (ClassUtils.isPrimitiveOrWrapper(actualType) || Number.class.isAssignableFrom(actualType)) {
                property = feedPrimitiveProperty(actualType);
            } else if (File.class.equals(actualType) || File.class.isAssignableFrom(actualType)) {
                property = new FileProperty();
            } else if (UUID.class.equals(actualType)) {
                property = new UUIDProperty();
            } else if (URI.class.equals(actualType)) {
                property = new StringProperty(StringProperty.Format.URI);
            } else if (URL.class.equals(actualType)) {
                property = new StringProperty(StringProperty.Format.URL);
            } else if (Date.class.isAssignableFrom(actualType)) {
                property = new DateProperty();
            } else {
                property = feedObjectProperty(typeInfo);
            }
        }

        return property;
    }

    private Property feedArrayProperty(TypeInformation typeInformation) {
        List<Class<?>> typeArguments = typeInformation.getTypeArguments();
        if (CollectionUtil.isNotEmpty(typeArguments) && typeArguments.size() == 1) {
            Class<?> typeArgument = typeArguments.get(0);
            ArrayProperty property = new ArrayProperty(parseType(typeArgument));

            if (Collection.class.isAssignableFrom(typeInformation.getActualType())) {
                property.setIsList(true);
            }

            if (Set.class.isAssignableFrom(typeInformation.getActualType())) {
                property.uniqueItems();
            } // XXX think about remaining properties
            return property;
        }
        throw new PropertyParserException("Not a valid array type property, No Type arguments available (or) More " +
                "than 1 type arguments available for type:" + typeArguments);
    }

    private Property feedMapProperty(TypeInformation typeInfo) {
        List<Class<?>> typeArguments = typeInfo.getTypeArguments();
        if (CollectionUtil.isNotEmpty(typeArguments) && typeArguments.size() == 2) {
            // 0:key, 1:value            Key:String (by default)
            return new MapProperty(parseType(typeArguments.get(1)));
        }
        throw new PropertyParserException("Not a valid map type property, No Type arguments available (or) More " +
                "than 2 type arguments available for type:" + typeArguments);
    }

    private Property feedPrimitiveProperty(Class<?> type) {
        Class<?> wrapperType = (type.isPrimitive()) ? ClassUtils.primitiveToWrapper(type) : type;
        Property property;
        if (Boolean.class.equals(wrapperType)) {
            property = new BooleanProperty();
        } else if (Character.class.equals(wrapperType)) {
            property = new StringProperty();
            ((StringProperty) property).setMaxLength(1);
        } else if (Byte.class.equals(wrapperType)) {
            property = new StringProperty(StringProperty.Format.BYTE);
        } else if (Number.class.isAssignableFrom(wrapperType)) {
            if (Long.class.equals(wrapperType) || BigInteger.class.equals(wrapperType)) {
                property = new LongProperty();
            } else if (Double.class.equals(wrapperType) || BigDecimal.class.equals(wrapperType)) {
                property = new DoubleProperty();
            } else if (Float.class.equals(wrapperType)) {
                property = new FloatProperty();
            } else {
                property = new IntegerProperty(); // cases like Integer, Short and any remaining
            }

        } else {
            property = new ObjectProperty(); // cases like Void.class
        }
        return property;
    }

    private Property feedStringProperty(Class<?> type) {
        StringProperty property = new StringProperty();

        if (DataTypeUtil.isEnum(type)) {
            property.setEnum(DataTypeUtil.getEnumValues(type));
        }

        return property;
    }

    private Property feedObjectProperty(TypeInformation typeInfo) {
        ContextUtil.parseModel(typeInfo.getActualType());
        RefProperty refProperty = new RefProperty(ContextUtil.getUniqueName(typeInfo.getActualType()));

        List<Property> typeArgProps = new LinkedList<>(); // adding type arguments
        for (final Class<?> type : typeInfo.getTypeArguments()) {
            typeArgProps.add(parseType(type));
        }

        refProperty.setTypeArguments(typeArgProps);
        return refProperty;
    }

    private boolean isArray(TypeInformation typeInformation) {
        return (typeInformation.isArray() || Collection.class.isAssignableFrom(typeInformation.getActualType()));
    }
}
