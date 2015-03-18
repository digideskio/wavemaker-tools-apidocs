/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.reflections.ReflectionUtils;

import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.ModelImpl;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.parser.parser.ModelParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.PropertyParser;
import com.wavemaker.tools.apidocs.tools.parser.util.DataTypeUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.MethodUtils;
import com.wavemaker.tools.apidocs.tools.parser.util.NonStaticMemberPredicate;
import com.wordnik.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 10/11/14
 */
public class ReflectionModelParser implements ModelParser {

    protected final Class<?> modelClass;

    public ReflectionModelParser(Class<?> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public Model parse() {
        assertClass();

        ModelImpl model = new ModelImpl();

        model.name(DataTypeUtil.getName(modelClass));
        if (modelClass.isAnnotationPresent(ApiModel.class)) {
            model.setDescription(modelClass.getAnnotation(ApiModel.class).description());
        }
        model.setProperties(getModelProperties());

        return model;
    }

    protected String getModelId() {
        return DataTypeUtil.getUniqueClassName(modelClass);
    }

    protected Map<String, Property> getModelProperties() {
        Map<String, Property> propertyMap;

        if (modelClass.isInterface()) {
            propertyMap = parsePropertiesUsingGetters(modelClass);
        } else {
            propertyMap = parsePropertiesUsingFields(modelClass);
        }

        return propertyMap;
    }

    protected Map<String, Property> parsePropertiesUsingGetters(Class<?> classToScan) {
        Map<String, Property> properties = new LinkedHashMap<>();

        Collection<Method> methods = MethodUtils.findGetterMethods(classToScan);

        for (final Method method : methods) {
            PropertyParser propertyParser = new PropertyParserImpl(method.getGenericReturnType());
            properties.put(MethodUtils.getPropertyName(method.getName()), propertyParser.parse());
        }

        return properties;
    }

    protected Map<String, Property> parsePropertiesUsingFields(Class<?> classToScan) {
        Map<String, Property> properties = new LinkedHashMap<>();
        Collection<Field> fields = ReflectionUtils.getAllFields(classToScan, NonStaticMemberPredicate.getInstance());
        for (Field field : fields) {
            PropertyParser parser = new PropertyParserImpl(field.getGenericType());
            properties.put(field.getName(), parser.parse());
        }
        return properties;
    }


    protected void assertClass() {
        if (modelClass == null) {
            throw new IllegalArgumentException("Invalid Model class");
        }
    }

}
