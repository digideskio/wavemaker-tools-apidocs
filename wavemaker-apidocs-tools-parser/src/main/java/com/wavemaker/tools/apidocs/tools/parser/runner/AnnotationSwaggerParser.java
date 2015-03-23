/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
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
