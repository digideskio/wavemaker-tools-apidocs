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
package com.wavemaker.tools.apidocs.tools.spring.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;

import com.wavemaker.tools.apidocs.tools.parser.impl.AbstractResourceParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.PathsParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class SpringResourceParser extends AbstractResourceParser {

    private static final String CONTROLLER_SUFFIX = "Controller";

    public SpringResourceParser(final Class<?> type) {
        super(type);
    }

    @Override
    protected Set<String> getProduces() {
        return new HashSet<>(Arrays.asList(type.getAnnotation(RequestMapping.class).produces()));
    }

    @Override
    protected Set<String> getConsumes() {
        return new HashSet<>(Arrays.asList(type.getAnnotation(RequestMapping.class).consumes()));
    }

    @Override
    protected String getResourceName() {
        final String typeSimpleName = type.getSimpleName();

        if (typeSimpleName.endsWith(CONTROLLER_SUFFIX)) {
            final int indexOf = typeSimpleName.lastIndexOf(CONTROLLER_SUFFIX);
            return typeSimpleName.substring(0, indexOf);
        }
        return typeSimpleName;
    }

    @Override
    protected String getResourcePath() {
        return type.getAnnotation(RequestMapping.class).value()[0];// fix this.
    }

    @Override
    protected PathsParser getPathParser(final Class<?> typeToParse) {
        return new SpringPathsParser(typeToParse);
    }

    @Override
    public boolean isRestApi() {
        return type.isAnnotationPresent(RequestMapping.class);
    }
}
