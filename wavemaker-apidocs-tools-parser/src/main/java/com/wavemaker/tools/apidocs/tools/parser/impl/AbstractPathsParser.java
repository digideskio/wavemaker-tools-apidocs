/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.Path;
import com.wavemaker.tools.apidocs.tools.core.utils.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.parser.context.ApiParserContext;
import com.wavemaker.tools.apidocs.tools.parser.parser.MethodParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.PathsParser;
import com.wavemaker.tools.apidocs.tools.parser.util.MethodUtils;
import com.wavemaker.tools.apidocs.tools.parser.util.Utils;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 11/11/14
 */
public abstract class AbstractPathsParser implements PathsParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPathsParser.class);
    protected final Class<?> controllerClass;

    protected AbstractPathsParser(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    @Override
    public Map<String, Path> parse() {
        List<Method> restMethods = new LinkedList<>(getRestMethods(controllerClass));
        LOGGER.debug("Rest Methods in {}:{}", controllerClass.getSimpleName(), restMethods.size());
        return extractOperations(restMethods);
    }


    /**
     * It will extracts the {@link Method} from given controller {@link Class}, and filters all rest methods from it.
     *
     * @param controllerClass class
     * @return Rest {@link Method}s.
     */
    protected Collection<Method> getRestMethods(Class<?> controllerClass) {
        Set<Method> methods = MethodUtils.getAllNonStaticMethods(controllerClass);
        LOGGER.debug("Methods found in {}:{}", controllerClass.getSimpleName(), methods.size());
        Collection<Method> restMethods = filterRestMethods(methods);
        if (CollectionUtil.isNotBlank(restMethods)) {
            return restMethods;
        }
        return Collections.emptyList();
    }

    /**
     * It will extracts the operations from given {@link Method}s, and groups by its path.
     *
     * @param restMethods methods to be scan.
     * @return {@link Map} of complete path vs {@link Path}s.
     */
    protected Map<String, Path> extractOperations(Collection<Method> restMethods) {
        Map<String, Path> pathMap = new HashMap<>();

        for (Method restMethod : restMethods) {
            LOGGER.debug("Parsing method:{}", restMethod);
            MethodParser methodParser = getMethodParser(restMethod);
            Operation operation = methodParser.parse();

            for (final String relativePath : methodParser.getPaths()) {
                String completePath = Utils.combinePaths(ApiParserContext.getContext().getResourcePath(), relativePath);
                Path path = pathMap.get(completePath);
                if (path == null) {
                    LOGGER.debug("found new path:{}", completePath);
                    path = new Path();
                    pathMap.put(completePath, path);
                }

                for (final String method : methodParser.getHttpMethods()) { // setting operation for each method.
                    path.set(method.toLowerCase(), operation);
                }
            }
        }

        return pathMap;
    }

    /**
     * It should returns list of Rest Methods from given list By reading Annotation or any other conditions.
     *
     * @param methods all controller methods
     * @return only Rest {@link Method}'s.
     */
    protected abstract Collection<Method> filterRestMethods(Collection<Method> methods);

    /**
     * It should return the {@link MethodParser} instance for parsing given {@link Method}.
     *
     * @param method the method needs to parse
     * @return {@link MethodParser} instance.
     */
    protected abstract MethodParser getMethodParser(Method method);
}
