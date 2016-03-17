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
package com.wavemaker.tools.apidocs.tools.parser.runner;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public abstract class AnnotationSwaggerParser extends SwaggerParser {

    public AnnotationSwaggerParser(final SwaggerConfiguration parserConfiguration) {
        super(parserConfiguration);
    }

    @Override
    protected Set<Class<?>> filterRestClasses(final Set<Class<?>> classSet) {


        Set<Class<?>> restClasses = new LinkedHashSet<>();

        for (Class<? extends Annotation> annotation : supportedRestAnnotations()) {
            for (Class<?> aClass : classSet) {
                if (aClass.isAnnotationPresent(annotation)) {
                    restClasses.add(aClass);
                }
            }
        }

        return restClasses;
    }

    /**
     * It should return the Set of annotations for filtering Rest {@link Class}es.
     *
     * @return {@link Set} of {@link Annotation} classes.
     */
    protected abstract Set<Class<? extends Annotation>> supportedRestAnnotations();

}
