/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.util;

import java.util.concurrent.Callable;

import com.wavemaker.tools.apidocs.tools.parser.exception.SwaggerParserException;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class ClassLoaderUtil {

    /**
     * Utility method to run {@link Callable} in specific {@link ClassLoader}.
     *
     * @param classLoader custom class loader where caller should runs.
     * @param callable    functions to execute.
     * @param <T>         return Type
     * @return T
     * @throws SwaggerParserException
     */
    public static <T> T doWithCustomClassLoader(ClassLoader classLoader, Callable<T> callable) throws Exception {

        ClassLoader defaultClassLoader = Thread.currentThread().getContextClassLoader(); // setting custom class loader.
        Thread.currentThread().setContextClassLoader(classLoader);

        try {
            return callable.call();
        } finally {
            Thread.currentThread().setContextClassLoader(defaultClassLoader); // resetting class loader.
        }

    }
}
