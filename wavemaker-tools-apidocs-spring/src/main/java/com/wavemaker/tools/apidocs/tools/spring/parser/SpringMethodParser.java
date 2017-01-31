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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.parser.impl.AbstractMethodParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.ParameterParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class SpringMethodParser extends AbstractMethodParser {

    protected SpringMethodParser(final Method methodToParse) {
        super(methodToParse);
    }

    @Override
    protected void handleFrameWorkSpecific(
            final Method methodToParse, final Operation operation) {
        RequestMapping requestMapping = methodToParse.getAnnotation(RequestMapping.class);

        operation.setConsumes(Arrays.asList(requestMapping.consumes()));
        operation.setProduces(Arrays.asList(requestMapping.produces()));

//        operation.setResponseMessages(new ArrayList<ResponseMessage>()); // TODO
//        operation.setPolicy(null); // TODO
    }

    @Override
    protected ParameterParser getParameterParser(
            final int index, final Type type, final Annotation[] annotations) {
        return new SpringParameterParser(index, type, annotations);
    }

    @Override
    public boolean isRestMethod() {
        return methodToParse.isAnnotationPresent(RequestMapping.class);
    }

    @Override
    public String[] getPaths() {
        RequestMapping requestMapping = methodToParse.getAnnotation(RequestMapping.class);
        final String[] value = requestMapping.value();

        String[] paths = new String[value.length];

        for (int i = 0; i < value.length; i++) {
            String path = value[i];
            // replacing spring pattern
            path = path.replace(":.+", "");
            path = path.replace(":.*", "");

            paths[i] = path;
        }

        return paths;
    }

    @Override
    public String[] getHttpMethods() {
        RequestMapping requestMapping = methodToParse.getAnnotation(RequestMapping.class);
        RequestMethod[] requestMethods = requestMapping.method();
        if (ArrayUtils.isNotEmpty(requestMethods)) {
            String[] methods = new String[requestMethods.length];
            for (int i = 0; i < requestMethods.length; i++) {
                methods[i] = requestMethods[i].name();
            }
            return methods;
        } else {
            return new String[]{"GET"}; // send default methods
        }
    }
}
