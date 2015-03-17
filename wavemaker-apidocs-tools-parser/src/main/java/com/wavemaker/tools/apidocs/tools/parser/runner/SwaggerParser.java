/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.runner;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.tools.apidocs.tools.core.model.ApiDocuments;
import com.wavemaker.tools.apidocs.tools.core.model.Resource;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.Path;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.Swagger;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.Tag;
import com.wavemaker.tools.apidocs.tools.parser.config.ApiParserConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.context.ApiParserContext;
import com.wavemaker.tools.apidocs.tools.parser.context.SwaggerParserContext;
import com.wavemaker.tools.apidocs.tools.parser.exception.ClassScannerException;
import com.wavemaker.tools.apidocs.tools.parser.exception.SwaggerParserException;
import com.wavemaker.tools.apidocs.tools.parser.exception.TimeOutException;
import com.wavemaker.tools.apidocs.tools.parser.util.ClassLoaderUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.MethodUtils;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public abstract class SwaggerParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerParser.class);

    private static final long TIME_OUT = 15;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    protected final ApiParserConfiguration parserConfiguration;

    public SwaggerParser(final ApiParserConfiguration parserConfiguration) {
        this.parserConfiguration = parserConfiguration;
    }

    /**
     * It will generates {@link ApiDocuments} from given {@link ApiParserConfiguration}.
     *
     * @return {@link ApiDocuments}
     * @throws SwaggerParserException
     */
    public Swagger generate() throws SwaggerParserException {
        try {
            long startTime = System.currentTimeMillis();
            Swagger swagger = doWithParserClassLoader();
            long endTime = System.currentTimeMillis();
            LOGGER.info("Api Parser Runner took {} milli seconds to generate documents", (endTime - startTime));
            return swagger;
        } catch (Exception e) {
            if (e instanceof SwaggerParserException) {
                throw (SwaggerParserException) e;
            }
            throw new SwaggerParserException("Exception while parsing documents", e);
        }
    }

    /**
     * It will returns the {@link Method} associated with given unique key.
     *
     * @param uniqueKey key to be search
     * @param type      type to search methods
     * @return {@link Method} matching with given uniqueKey.
     */
    public Method getMethodForUniqueKey(String uniqueKey, Class<?> type) {
        return MethodUtils.getMethodForUniqueIdentifier(uniqueKey, type);
    }

    /**
     * It will returns the {@link Map} of unique key vs {@link Method} for a given {@link Class}.
     *
     * @param type class to be scan for methods.
     * @return {@link Map} of unique vs {@link Method}s.
     */
    public Map<String, Method> getUniqueKeyVsMethods(Class<?> type) {
        return MethodUtils.getMethodUniqueIdentifierIdMap(type);
    }

    /**
     * It will calls the {@link #startProcessing()} in a custom class loader.
     *
     * @return {@link Map}.
     * @throws Exception
     */
    protected Swagger doWithParserClassLoader() throws Exception {
        return ClassLoaderUtil.doWithCustomClassLoader(parserConfiguration.getClassLoader(),
                new Callable<Swagger>() {
                    @Override
                    public Swagger call() throws Exception {
                        return startProcessing();
                    }
                });
    }

    /**
     * It will scans given classes, then filters the rest classes and finally returns the Map of {@link Resource}s.
     *
     * @return {@link Map} of Rest {@link Class} and {@link Resource}.
     */
    protected Swagger startProcessing() throws SwaggerParserException {
        SwaggerParserContext.initContext(parserConfiguration);
        Set<Class<?>> classToScan = parserConfiguration.getClassScanner().classesToScan();
        Set<Class<?>> restClasses;
        try {
            restClasses = filterRestClasses(classToScan);
        } catch (Exception e) {
            LOGGER.error("Error while scanning classes:", e);
            throw new ClassScannerException(e);
        }
        Swagger swagger;
        try {
            swagger = generateDocuments(restClasses);
        } catch (InterruptedException e) {
            LOGGER.error("Error while generating documents", e);
            throw new TimeOutException("Error while generating documents", e);
        }
        SwaggerParserContext.destroyContext();
        return swagger;
    }


    /**
     * Actual Class parsing starts here. It will parse all given classes and returns {@link Map} of {@link Class} vs
     * {@link Resource}.
     *
     * @param restClasses rest classes to look for documents.
     * @return {@link Map} of {@link Class} vs {@link Resource}s.
     */
    protected Swagger generateDocuments(Set<Class<?>> restClasses) throws InterruptedException {
        final Map<Class<?>, Resource> resourceMap = new ConcurrentHashMap<>();
        ExecutorService executorService = new ThreadPoolExecutor(
                SwaggerParserContext.getInstance().getParserConfiguration().getCoreThreadPoolSize(),
                SwaggerParserContext.getInstance().getParserConfiguration().getMaxThreadPoolSize(), 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (final Class<?> restClass : restClasses) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    LOGGER.debug("Started parsing {} controller", restClass);
                    ApiParserContext.initContext();// creating parser context for each class.
                    try {
                        resourceMap.put(restClass, parseRestClass(restClass));
                    } finally {
                        ApiParserContext.destroyContext(); // destroying parser context after parsing.
                    }
                }
            });
        }
        executorService.shutdown();
        LOGGER.debug("Waiting to finish parsing documents, max time limit of {} {}.", TIME_OUT, TIME_UNIT);
        executorService.awaitTermination(TIME_OUT, TIME_UNIT);
        return swaggerFrom(resourceMap);
    }

    protected Swagger swaggerFrom(Map<Class<?>, Resource> resourceMap) {
        Swagger swagger = new Swagger();
        for (final Map.Entry<Class<?>, Resource> resourceEntry : resourceMap.entrySet()) {
            Resource resource = resourceEntry.getValue();
            Tag tag = resource.asTag();
            swagger.tag(tag);
            resource.setTag(tag.getName());
            for (final Map.Entry<String, Path> pathEntry : resource.getPathMap().entrySet()) {
                swagger.path(pathEntry.getKey(), pathEntry.getValue());
            }
        }


        return swagger;
    }


    /**
     * It should parses the given restClass and generates {@link Resource}.
     *
     * @param restClass class to be parsed.
     * @return generated {@link Resource}.
     */
    protected abstract Resource parseRestClass(Class<?> restClass);


    /**
     * It should filters the Rest classes from given set of classes.
     *
     * @param classSet list of classes to filter
     * @return {@link Set} of Rest {@link Class}es.
     */
    protected abstract Set<Class<?>> filterRestClasses(Set<Class<?>> classSet);
}
