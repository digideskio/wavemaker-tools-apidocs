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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.wavemaker.tools.apidocs.tools.core.model.AbstractModel;
import com.wavemaker.tools.apidocs.tools.core.model.ComposedModel;
import com.wavemaker.tools.apidocs.tools.core.model.Constraint;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.ModelImpl;
import com.wavemaker.tools.apidocs.tools.core.model.RefModel;
import com.wavemaker.tools.apidocs.tools.core.model.constraint.BasicConstraint;
import com.wavemaker.tools.apidocs.tools.core.model.properties.AbstractProperty;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.parser.parser.ModelParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.PropertyParser;
import com.wavemaker.tools.apidocs.tools.parser.util.AnnotationUtils;
import com.wavemaker.tools.apidocs.tools.parser.util.ConstraintsUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.ContextUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.DataTypeUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.MethodUtils;
import com.wavemaker.tools.apidocs.tools.parser.util.NonStaticMemberPredicate;
import com.wavemaker.tools.apidocs.tools.parser.util.Utils;
import com.wordnik.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 10/11/14
 */
public class ReflectionModelParser implements ModelParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionModelParser.class);

    protected final Class<?> modelClass;

    public ReflectionModelParser(Class<?> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public Model parse() {
        assertClass();
        Model model;
        List<Class<?>> superTypes = Utils.getAllFilteredSuperTypes(modelClass);
        if (!superTypes.isEmpty()) {
            model = parseComposedClass(modelClass, superTypes);
        } else {
            model = parseClass(modelClass);
        }
        ((AbstractModel) model).setFullyQualifiedName(DataTypeUtil.getFullyQualifiedName(modelClass));

        return model;
    }

    protected Model parseComposedClass(Class<?> classToScan, List<Class<?>> superTypes) {
        ComposedModel composedModel = new ComposedModel();

        // combining parent and interfaces
        List<Model> refModels = new LinkedList<>();
        for (final Class<?> superType : superTypes) {
            Optional<RefModel> modelOptional = ContextUtil.parseModel(superType);
            if (modelOptional.isPresent()) {
                refModels.add(modelOptional.get());
            }
        }
        composedModel.setAllOf(refModels);
        composedModel.child(parseClass(classToScan));
        return composedModel;
    }


    protected Model parseClass(Class<?> classToScan) {
        ModelImpl model = new ModelImpl();

        model.name(DataTypeUtil.getName(classToScan));
        if (classToScan.isAnnotationPresent(ApiModel.class)) {
            model.setDescription(classToScan.getAnnotation(ApiModel.class).description());
        }
        model.setProperties(getModelProperties(classToScan));
        model.setRequired(findRequiredFields(model.getProperties()));

        return model;
    }

    protected Map<String, Property> getModelProperties(Class<?> classToScan) {

        Map<String, Property> propertyMap;
        if (classToScan.isInterface()) {
            propertyMap = parsePropertiesUsingGetters(classToScan);
        } else {
            propertyMap = parsePropertiesUsingFields(classToScan);
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
        Collection<Field> fields = Collections.EMPTY_LIST;
        try {
            fields = ReflectionUtils.getFields(classToScan, NonStaticMemberPredicate.getInstance());
        } catch (Throwable th) {
            // XXX should throw? if we throw error entire swagger generation will get failed.
            // Here issues with class loader, like NoClassDefFoundError and ClassNotFound exception.
            LOGGER.error("Error while reading fields for class:{}", classToScan, th);
        }
        for (Field field : fields) {
            PropertyParser parser = new PropertyParserImpl(field.getGenericType());
            final Property property = parser.parse();
            property.setRequired(isRequired(field));
            List<Annotation> constraintAnnotations = ConstraintsUtil.getConstraintAnnotations(field);
            if (!constraintAnnotations.isEmpty()) {
                List<Constraint> constraintList = new ArrayList<>(constraintAnnotations.size());
                for (Annotation constraintAnnotation : constraintAnnotations) {
                    Map<String, Object> constraintAnnotationParametersMap = ConstraintsUtil.getConstraintAnnotationParametersMap(constraintAnnotation);
                    BasicConstraint basicConstraint = new BasicConstraint();
                    basicConstraint.setConstraintType(constraintAnnotation.annotationType().getSimpleName());
                    basicConstraint.setConstraintParameters(constraintAnnotationParametersMap);
                    constraintList.add(basicConstraint);
                }
                ((AbstractProperty) property).setConstraints(constraintList);
            }
            property.setReadOnly(isReadOnly(field));
            properties.put(findFieldName(field), property);
        }
        return properties;
    }

    protected String findFieldName(Field field) {
        String name = field.getName();
        final Optional<JsonProperty> jsonPropertyOptional = AnnotationUtils
                .findAnnotationForProperty(field, JsonProperty.class);
        if (jsonPropertyOptional.isPresent()) {
            final String value = jsonPropertyOptional.get().value();
            if (StringUtils.isNotBlank(value)) {
                name = value;
            }
        }
        return name;
    }

    protected boolean isReadOnly(Field field) {
        boolean readOnly = false;

        final Optional<JsonProperty> jsonPropertyOptional = AnnotationUtils
                .findAnnotationForProperty(field, JsonProperty.class);

        if (jsonPropertyOptional.isPresent()) {
            final JsonProperty.Access access = jsonPropertyOptional.get().access();
            readOnly = (access == JsonProperty.Access.READ_ONLY);
        }

        return readOnly;
    }

    protected boolean isRequired(Field field) {
        boolean required = AnnotationUtils.findAnnotationForProperty(field, NotNull.class).isPresent();

        if (!required) {
            final Optional<JsonProperty> jsonPropertyOptional = AnnotationUtils
                    .findAnnotationForProperty(field, JsonProperty.class);
            if (jsonPropertyOptional.isPresent()) {
                required = jsonPropertyOptional.get().required();
            }
        }

        return required;
    }

    protected List<String> findRequiredFields(Map<String, Property> propertyMap) {
        List<String> requiredFields = new ArrayList<>();

        if (propertyMap != null) {
            for (final Map.Entry<String, Property> entry : propertyMap.entrySet()) {
                if (entry.getValue().getRequired()) {
                    requiredFields.add(entry.getKey());
                }
            }
        }

        return requiredFields;
    }

    protected void assertClass() {
        if (modelClass == null) {
            throw new IllegalArgumentException("Invalid Model class");
        }
    }

}
