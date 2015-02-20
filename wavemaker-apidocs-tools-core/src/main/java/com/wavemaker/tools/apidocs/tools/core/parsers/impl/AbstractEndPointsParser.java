/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.parsers.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.tools.apidocs.tools.core.context.ApiParserContext;
import com.wavemaker.tools.apidocs.tools.core.model.EndPoint;
import com.wavemaker.tools.apidocs.tools.core.model.FoundTypesWrapper;
import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.parser.EndPointsParser;
import com.wavemaker.tools.apidocs.tools.core.parser.MethodParser;
import com.wavemaker.tools.apidocs.tools.core.util.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.core.util.MethodUtils;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 11/11/14
 */
public abstract class AbstractEndPointsParser implements EndPointsParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEndPointsParser.class);
    protected final Class<?> controllerClass;

    protected AbstractEndPointsParser(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    @Override
    public List<EndPoint> parse() {
        List<Method> restMethods = new LinkedList<>(getRestMethods(controllerClass));
        LOGGER.debug("Rest Methods in {}:{}", controllerClass.getSimpleName(), restMethods.size());
        Map<String, List<Operation>> pathVsOperationsMap = extractOperations(restMethods);
        return generateEndPoints(pathVsOperationsMap);
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
     * @return {@link Map} of path vs list of {@link Operation}s.
     */
    protected Map<String, List<Operation>> extractOperations(Collection<Method> restMethods) {
        Map<String, List<Operation>> pathVsOperationsMap = new LinkedHashMap<>();

        for (Method restMethod : restMethods) {
            LOGGER.debug("Parsing method:{}", restMethod);
            MethodParser methodParser = getMethodParser(restMethod);
            final FoundTypesWrapper<Operation> typesWrapper = methodParser.parse();
            Operation operation = typesWrapper.getModel();

            if (!pathVsOperationsMap.containsKey(methodParser.getPath())) { // if path not already exits,
                // creating an entry.
                LOGGER.debug("found new end point:{}", methodParser.getPath());
                pathVsOperationsMap.put(methodParser.getPath(), new LinkedList<Operation>());
            }

            // adding operation to the existing path.
            pathVsOperationsMap.get(methodParser.getPath()).add(operation);

            // parsing types.
            ApiParserContext.getContext().getParsersChain().processTypes(typesWrapper.getFoundTypes());
        }

        return pathVsOperationsMap;
    }

    /**
     * It will generates {@link EndPoint} list form pathVsOperationsMap {@link Map}.
     *
     * @param pathVsOperationsMap extracted {@link Operation} info.
     * @return list of {@link EndPoint}
     */
    protected List<EndPoint> generateEndPoints(Map<String, List<Operation>> pathVsOperationsMap) {
        List<EndPoint> endPoints = new LinkedList<>();

        for (Map.Entry<String, List<Operation>> entry : pathVsOperationsMap.entrySet()) {
            endPoints.add(generateEndPoint(entry.getKey(), entry.getValue()));
        }

        return endPoints;
    }

    /**
     * It will creates the {@link EndPoint} from given path and {@link Operation}.
     *
     * @param path       endpoint path
     * @param operations endpoint operations
     * @return {@link EndPoint} with given parameters.
     */
    protected EndPoint generateEndPoint(String path, List<Operation> operations) {
        EndPoint endPoint = new EndPoint();
        endPoint.setRelativePath(path); // XXX do we need to add api path also?
        endPoint.setOperations(operations);
        endPoint.setDescription("");// TODO
        endPoint.setName(path); // XXX do we have alternative info?

        return endPoint;
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
