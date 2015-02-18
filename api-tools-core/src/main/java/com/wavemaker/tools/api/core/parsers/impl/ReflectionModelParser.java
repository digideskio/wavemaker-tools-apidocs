/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.parsers.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.reflections.ReflectionUtils;

import com.wavemaker.tools.api.core.models.Model;
import com.wavemaker.tools.api.core.models.Property;
import com.wavemaker.tools.api.core.models.TypeInformationWrapper;
import com.wavemaker.tools.api.core.parsers.ModelParser;
import com.wavemaker.tools.api.core.parsers.PropertyParser;
import com.wavemaker.tools.api.core.utils.DataTypeUtil;
import com.wavemaker.tools.api.core.utils.MethodUtils;
import com.wavemaker.tools.api.core.utils.NonStaticMemberPredicate;
import com.wordnik.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 10/11/14
 */
public class ReflectionModelParser extends AbstractFoundTypesParserContainer<Model> implements ModelParser {

    protected final Class<?> modelClass;

    public ReflectionModelParser(Class<?> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public Model parseInternal() {
        assertClass();

        Model model = new Model();

        model.setId(getModelId());
        model.setName(DataTypeUtil.getName(modelClass));
        if (modelClass.isAnnotationPresent(ApiModel.class)) {
            model.setDescription(modelClass.getAnnotation(ApiModel.class).description());
        }
        model.setProperties(getModelProperties());

        return model;
    }

    protected String getModelId() {
        return DataTypeUtil.getUniqueClassName(modelClass);
    }

    protected List<Property> getModelProperties() {
        List<Property> properties;

        if (modelClass.isInterface()) {
            properties = parsePropertiesUsingGetters(modelClass);
        } else {
            properties = parsePropertiesUsingFields(modelClass);
        }

        return properties;
    }

    protected List<Property> parsePropertiesUsingGetters(Class<?> classToScan) {
        List<Property> properties = new LinkedList<>();

        Collection<Method> methods = MethodUtils.findGetterMethods(classToScan);

        for (final Method method : methods) {
            ReflectivePropertyParser propertyParser = new ReflectivePropertyParser(method.getGenericReturnType(),
                    MethodUtils.getPropertyName(method.getName()));
            TypeInformationWrapper<Property> propertyWrapper = propertyParser.parse();
            properties.add(propertyWrapper.getModel());
            addNewTypes(propertyWrapper.getTypeInformation().getFoundTypes());
        }

        return properties;
    }

    protected List<Property> parsePropertiesUsingFields(Class<?> classToScan) {
        List<Property> properties = new LinkedList<>();
        Collection<Field> fields = ReflectionUtils.getAllFields(classToScan, NonStaticMemberPredicate.getInstance());
        for (Field field : fields) {
            PropertyParser parser = new FieldPropertyParser(field);
            final TypeInformationWrapper<Property> informationWrapper = parser.parse();
            properties.add(informationWrapper.getModel());
            addNewTypes(informationWrapper.getTypeInformation().getFoundTypes());
        }
        return properties;
    }


    protected void assertClass() {
        if (modelClass == null) {
            throw new IllegalArgumentException("Invalid Model class");
        }
    }
}
