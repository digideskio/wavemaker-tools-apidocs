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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.Constraint;

import org.apache.commons.lang3.ClassUtils;
import org.reflections.ReflectionUtils;

import com.google.common.collect.Sets;

/**
 * @author Uday Shankar
 */
public class ConstraintsUtil {

    private static Set<String> ignoredFields = Sets.newHashSet("groups", "payload");

    public static List<Annotation> getConstraintAnnotations(Field field) {
        List<Annotation> annotationList = new ArrayList<>();
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationClass = annotation.annotationType();
            Annotation[] annotationClassAnnotations = annotationClass.getAnnotations();
            boolean isConstraintClass = false;
            for (Annotation annotationClassAnnotation : annotationClassAnnotations) {
                if (annotationClassAnnotation.annotationType().equals(Constraint.class)) {
                    isConstraintClass = true;
                }
            }
            if (isConstraintClass) {
                annotationList.add(annotation);
            }
        }
        return annotationList;
    }

    public static Map<String, Object> getConstraintAnnotationParametersMap(Annotation annotation) {
        if (Proxy.isProxyClass(annotation.getClass())) {
            Object o = invokeField(annotation.getClass(), "h", annotation);
            Class annotationOriginalClass = (Class) invokeField(o.getClass(), "type", o);
            Map memberValuesMap = (LinkedHashMap) invokeField(o.getClass(), "memberValues", o);
            Map<String, Object> parametersMap = new LinkedHashMap<>();
            for (Object entryKey : memberValuesMap.keySet()) {
                String key = (String) entryKey;
                Object value = memberValuesMap.get(key);
                Method method = getMethod(annotationOriginalClass, key);
                if (canConstraintMemberBeAdded(method, value)) {
                    parametersMap.put(key, value);
                }
            }
            return parametersMap;
        } else {
            Class<? extends Annotation> annotationClass = annotation.annotationType();
            Method[] methods = annotationClass.getMethods();
            Map<String, Object> parametersMap = new LinkedHashMap<>();
            for (Method method : methods) {
                String methodName = method.getName();
                try {
                    Object value = method.invoke(annotation);
                    if (canConstraintMemberBeAdded(method, value)) {
                        parametersMap.put(methodName, value);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to get constraint annotation parameters for method [" + method + "]", e);
                }
            }
            return parametersMap;
        }
    }

    private static boolean canConstraintMemberBeAdded(Method method, Object value) {
        if (value == null || ignoredFields.contains(method.getName())) {
            return false;
        }
        if (ClassUtils.isPrimitiveOrWrapper(value.getClass())) {
            return true;
        } else {
            return !Objects.deepEquals(value, method.getDefaultValue());
        }
    }

    public static Object invokeField(Class c, String fieldName, Object o) {
        Set<Field> fields = org.reflections.ReflectionUtils.getAllFields(c, org.reflections.ReflectionUtils.withName(fieldName));
        if (fields== null || fields.size() == 0) {
            throw new RuntimeException("No such field [" + fieldName + "]");
        }
        Field field = fields.iterator().next();
        field.setAccessible(true);
        try {
            return field.get(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get field value", e);
        }
    }

    public static Method getMethod(Class c, String methodName, Class<?>... types) {
        Set<Method> methods = ReflectionUtils.getMethods(c, ReflectionUtils.withName(methodName), ReflectionUtils.withParameters(types));
        if (methods == null || methods.size() == 0) {
            throw new RuntimeException("No such method [" + methodName + "]");
        }
        if (methods.size() > 1) {
            throw new RuntimeException("Multiple methods matching with name [" + methodName + "]");
        }
        return methods.iterator().next();
    }

}
