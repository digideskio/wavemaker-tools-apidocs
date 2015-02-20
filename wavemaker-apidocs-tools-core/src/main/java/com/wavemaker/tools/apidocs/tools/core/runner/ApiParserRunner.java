/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.runner;

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

import com.wavemaker.tools.apidocs.tools.core.config.ApiParserConfiguration;
import com.wavemaker.tools.apidocs.tools.core.context.ApiParserContext;
import com.wavemaker.tools.apidocs.tools.core.context.ParserRunnerContext;
import com.wavemaker.tools.apidocs.tools.core.exception.ApiParserRunnerException;
import com.wavemaker.tools.apidocs.tools.core.exception.ClassScannerException;
import com.wavemaker.tools.apidocs.tools.core.exception.TimeOutException;
import com.wavemaker.tools.apidocs.tools.core.model.ApiDocument;
import com.wavemaker.tools.apidocs.tools.core.model.ApiDocuments;
import com.wavemaker.tools.apidocs.tools.core.util.ClassLoaderUtil;
import com.wavemaker.tools.apidocs.tools.core.util.MethodUtils;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public abstract class ApiParserRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiParserRunner.class);

    private static final long TIME_OUT = 15;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    protected final ApiParserConfiguration parserConfiguration;

    public ApiParserRunner(final ApiParserConfiguration parserConfiguration) {
        this.parserConfiguration = parserConfiguration;
    }

    /**
     * It will generates {@link ApiDocuments} from given {@link ApiParserConfiguration}.
     *
     * @return {@link ApiDocuments}
     * @throws ApiParserRunnerException
     */
    public ApiDocuments generate() throws ApiParserRunnerException {
        try {
            long startTime = System.currentTimeMillis();
            ApiDocuments documents = new ApiDocuments(doWithParserClassLoader());
            long endTime = System.currentTimeMillis();
            LOGGER.info("Api Parser Runner took {} milli seconds to generate documents", (endTime - startTime));
            return documents;
        } catch (Exception e) {
            if (e instanceof ApiParserRunnerException) {
                throw (ApiParserRunnerException) e;
            }
            throw new ApiParserRunnerException("Exception while parsing documents", e);
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
    protected Map<Class<?>, ApiDocument> doWithParserClassLoader() throws Exception {
        return ClassLoaderUtil.doWithCustomClassLoader(parserConfiguration.getClassLoader(),
                new Callable<Map<Class<?>, ApiDocument>>() {
                    @Override
                    public Map<Class<?>, ApiDocument> call() throws Exception {
                        return startProcessing();
                    }
                });
    }

    /**
     * It will scans given classes, then filters the rest classes and finally returns the Map of {@link ApiDocument}s.
     *
     * @return {@link Map} of Rest {@link Class} and {@link ApiDocument}.
     */
    protected Map<Class<?>, ApiDocument> startProcessing() throws ApiParserRunnerException {
        ParserRunnerContext.initContext(parserConfiguration);
        Set<Class<?>> classToScan = parserConfiguration.getClassScanner().classesToScan();
        Set<Class<?>> restClasses;
        try {
            restClasses = filterRestClasses(classToScan);
        } catch (Exception e) {
            LOGGER.error("Error while scanning classes:", e);
            throw new ClassScannerException(e);
        }
        Map<Class<?>, ApiDocument> documentMap;
        try {
            documentMap = generateDocuments(restClasses);
        } catch (InterruptedException e) {
            LOGGER.error("Error while generating documents", e);
            throw new TimeOutException("Error while generating documents", e);
        }
        ParserRunnerContext.destroyContext();
        return documentMap;
    }


    /**
     * Actual Class parsing starts here. It will parse all given classes and returns {@link Map} of {@link Class} vs
     * {@link ApiDocument}.
     *
     * @param restClasses rest classes to look for documents.
     * @return {@link Map} of {@link Class} vs {@link ApiDocument}s.
     */
    protected Map<Class<?>, ApiDocument> generateDocuments(Set<Class<?>> restClasses) throws InterruptedException {
        final Map<Class<?>, ApiDocument> apiDocumentMap = new ConcurrentHashMap<>();
        ExecutorService executorService = new ThreadPoolExecutor(
                ParserRunnerContext.getInstance().getParserConfiguration().getCoreThreadPoolSize(),
                ParserRunnerContext.getInstance().getParserConfiguration().getMaxThreadPoolSize(), 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (final Class<?> restClass : restClasses) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    LOGGER.debug("Started parsing {} controller", restClass);
                    ApiParserContext.initContext();// creating parser context for each class.
                    try {
                        apiDocumentMap.put(restClass, parseRestClass(restClass));
                    } finally {
                        ApiParserContext.destroyContext(); // destroying parser context after parsing.
                    }
                }
            });
        }
        executorService.shutdown();
        LOGGER.debug("Waiting to finish parsing documents, max time limit of {} {}.", TIME_OUT, TIME_UNIT);
        executorService.awaitTermination(TIME_OUT, TIME_UNIT);
        return apiDocumentMap;
    }


    /**
     * It should parses the given restClass and generates {@link ApiDocument}.
     *
     * @param restClass class to be parsed.
     * @return generated {@link ApiDocument}.
     */
    protected abstract ApiDocument parseRestClass(Class<?> restClass);


    /**
     * It should filters the Rest classes from given set of classes.
     *
     * @param classSet list of classes to filter
     * @return {@link Set} of Rest {@link Class}es.
     */
    protected abstract Set<Class<?>> filterRestClasses(Set<Class<?>> classSet);
}
