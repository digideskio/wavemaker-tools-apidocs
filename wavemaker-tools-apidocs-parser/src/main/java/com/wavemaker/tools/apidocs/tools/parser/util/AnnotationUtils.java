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
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Optional;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 7/6/17
 */
public class AnnotationUtils {

    public static <T extends Annotation> Optional<T> findAnnotationForProperty(Field field, Class<T> annotation) {
        Optional<T> optional = findAnnotation(field, annotation);

        if (!optional.isPresent()) {
            final Optional<Method> getter = findGetter(field);
            if (getter.isPresent()) {
                optional = findAnnotation(getter.get(), annotation);
            }
        }

        if (!optional.isPresent()) {
            final Optional<Method> setter = findSetter(field);
            if (setter.isPresent()) {
                optional = findAnnotation(setter.get(), annotation);
            }
        }

        return optional;
    }

    public static <T extends Annotation> Optional<T> findAnnotation(AnnotatedElement element, Class<T> annotation) {
        if (element.isAnnotationPresent(annotation)) {
            return Optional.of(element.getAnnotation(annotation));
        } else {
            return Optional.absent();
        }
    }

    private static Optional<Method> findGetter(final Field field) {
        Method getter = null;
        try {
            getter = field.getDeclaringClass()
                    .getDeclaredMethod("get" + StringUtils.capitalize(field.getName()));
        } catch (NoSuchMethodException e) {
            try {
                getter = field.getDeclaringClass()
                        .getDeclaredMethod("is" + StringUtils.capitalize(field.getName()));
            } catch (NoSuchMethodException e1) {
                // ignore
            }
        }
        return Optional.fromNullable(getter);
    }

    private static Optional<Method> findSetter(final Field field) {
        Method setter = null;
        try {
            setter = field.getDeclaringClass()
                    .getDeclaredMethod("set" + StringUtils.capitalize(field.getName()), field.getType());
        } catch (NoSuchMethodException e) {
            // ignore
        }
        return Optional.fromNullable(setter);
    }
}
