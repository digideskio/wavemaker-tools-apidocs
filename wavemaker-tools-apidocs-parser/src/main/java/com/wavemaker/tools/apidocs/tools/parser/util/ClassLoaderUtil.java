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
