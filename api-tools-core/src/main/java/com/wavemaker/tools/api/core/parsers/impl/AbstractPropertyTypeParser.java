/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.parsers.impl;

import java.lang.reflect.Type;

import org.apache.commons.lang3.ClassUtils;

import com.wavemaker.tools.api.core.builders.PrimitiveType;
import com.wavemaker.tools.api.core.builders.PropertyBuilder;
import com.wavemaker.tools.api.core.models.Property;
import com.wavemaker.tools.api.core.models.TypeInformation;
import com.wavemaker.tools.api.core.models.TypeInformationWrapper;
import com.wavemaker.tools.api.core.parsers.BasePropertyParser;
import com.wavemaker.tools.api.core.utils.DataTypeUtil;
import com.wavemaker.tools.api.core.utils.TypeUtil;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public abstract class AbstractPropertyTypeParser<T extends Property, Builder extends PropertyBuilder> implements
        BasePropertyParser<T> {

    protected Type type;

    protected AbstractPropertyTypeParser(Type type) {
        this.type = type;
    }

    @Override
    public TypeInformationWrapper<T> parse() {
        Builder builder = newBuilder();
        TypeInformation typeInfo = TypeUtil.extractTypeInformation(type);
        generateTypeInfo(typeInfo.getActualType(), builder);
        builder.setTypeArguments(DataTypeUtil.getUniqueClassNames(typeInfo.getTypeArguments()));

        T property = postProcessBuilder(builder);

        return new TypeInformationWrapper<>(property, typeInfo);
    }


    protected void generateTypeInfo(Class<?> classType, Builder builder) {
        builder.setType(DataTypeUtil.getUniqueClassName(classType));
        builder.setFullyQualifiedType(classType);
        builder.setId(ClassUtils.getShortClassName(classType));

        if (DataTypeUtil.isEnum(classType)) {
            handleEnum(classType, builder);
        } else if (DataTypeUtil.isPrimitiveType(classType)) {
            builder.setType(DataTypeUtil.getPrimitiveType(classType));
        } else {
            builder.setType(DataTypeUtil.getUniqueClassName(classType));
        }
    }

    protected void handleEnum(Class<?> enumClass, PropertyBuilder builder) {
        builder.setType(PrimitiveType.STRING);
        builder.setAllowableValues(DataTypeUtil.getEnumValues(enumClass));
    }

    protected abstract T postProcessBuilder(Builder builder);

    protected abstract Builder newBuilder();
}
