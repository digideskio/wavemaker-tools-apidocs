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
package com.wavemaker.tools.apidocs.tools.spring.parser;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.wavemaker.tools.apidocs.tools.parser.impl.AbstractPathsParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.MethodParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class SpringPathsParser extends AbstractPathsParser {
    protected SpringPathsParser(final Class<?> controllerClass) {
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
