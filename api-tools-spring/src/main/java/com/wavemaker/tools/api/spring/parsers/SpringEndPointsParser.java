/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.spring.parsers;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.wavemaker.tools.api.core.parsers.MethodParser;
import com.wavemaker.tools.api.core.parsers.impl.AbstractEndPointsParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class SpringEndPointsParser extends AbstractEndPointsParser {
    protected SpringEndPointsParser(final Class<?> controllerClass) {
        super(controllerClass);
    }

    @Override
    protected Collection<Method> filterRestMethods(final Collection<Method> methods) {
        List<Method> filteredMethods = new LinkedList<>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                filteredMethods.add(method);
            }
        }

        return filteredMethods;
    }

    @Override
    protected MethodParser getMethodParser(final Method method) {
        return new SpringMethodParser(method);
    }
}
