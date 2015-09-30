/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ClassUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wavemaker.tools.apidocs.tools.core.model.Info;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;
import com.wavemaker.tools.apidocs.tools.core.model.Tag;
import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.exception.SwaggerParserException;
import com.wavemaker.tools.apidocs.tools.parser.runner.SwaggerParser;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableClassScanner;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableModelScanner;
import com.wavemaker.tools.apidocs.tools.spring.controller.DepartmentController;
import com.wavemaker.tools.apidocs.tools.spring.controller.QueryExecutionController;
import com.wavemaker.tools.apidocs.tools.spring.controller.UserController;
import com.wavemaker.tools.apidocs.tools.spring.controller.VacationController;
import com.wavemaker.tools.apidocs.tools.spring.resolver.MultiPartFileResolver;
import com.wavemaker.tools.apidocs.tools.spring.resolver.MultiPartRequestResolver;
import com.wavemaker.tools.apidocs.tools.spring.resolver.PageParameterResolver;
import com.wavemaker.tools.apidocs.tools.spring.resolver.ServletMetaTypesResolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SpringSwaggerParserTest {

    public static final int NO_OF_CONTROLLERS = 6;

    @Test
    public void testGenerate() throws Exception {
        FilterableClassScanner classScanner = new FilterableClassScanner();
        final String packageName = "com.wavemaker.tools.apidocs.tools";
        classScanner.includePackage(packageName);
        Info info = new Info();
        info.description("hrdb database service apis");
        info.setTitle("HRDB service");
        SwaggerConfiguration.Builder builder = new SwaggerConfiguration.Builder("/test", classScanner);
        builder.setInfo(info);
        builder.setClassLoader(this.getClass().getClassLoader());
        SwaggerParser runner = new SpringSwaggerParser(builder.build());
        Swagger swagger = runner.generate();
        assertNotNull(swagger);
        assertFalse(swagger.getTags().isEmpty());
        assertEquals(NO_OF_CONTROLLERS, swagger.getTags().size());

        for (final Tag tag : swagger.getTags()) {
            final String fullyQualifiedName = tag.getFullyQualifiedName();
            final String classPackage = ClassUtils.getPackageName(fullyQualifiedName);
            assertTrue(classPackage.startsWith(packageName + "."));
        }
        writeToFile(swagger, "swagger.json");
    }

    @Test
    public void testGenerateWithPackagePrefix() throws Exception {
        FilterableClassScanner classScanner = new FilterableClassScanner();
        final String packageName = "com.wavemaker.tools.apidocs.tools.spring.controller";
        classScanner.includePackage(packageName);
        Info info = new Info();
        info.description("hrdb database service apis");
        info.setTitle("HRDB service");
        SwaggerConfiguration.Builder builder = new SwaggerConfiguration.Builder("/test", classScanner);
        builder.setInfo(info);
        builder.setClassLoader(this.getClass().getClassLoader());
        SwaggerParser runner = new SpringSwaggerParser(builder.build());
        Swagger swagger = runner.generate();

        assertFalse(swagger.getTags().isEmpty());
        for (final Tag tag : swagger.getTags()) {
            final String fullyQualifiedName = tag.getFullyQualifiedName();
            final String classPackage = ClassUtils.getPackageName(fullyQualifiedName);
            assertEquals(packageName, classPackage);
        }
    }

    @Test
    public void testGenerateWithResolvers() throws Exception {
        FilterableClassScanner classScanner = new FilterableClassScanner();
        classScanner.includePackage("com.wavemaker.tools.apidocs.tools");
        SwaggerConfiguration.Builder builder = new SwaggerConfiguration.Builder("/test", classScanner);
        builder.setClassLoader(this.getClass().getClassLoader());
        addDefaultParameterResolvers(builder);
        SwaggerParser runner = new SpringSwaggerParser(builder.build());
        Swagger swagger = runner.generate();

        writeToFile(swagger, "swagger_with_resolvers.json");
    }

    @Test
    public void testModelScanner() throws Exception {
        FilterableModelScanner modelScanner = new FilterableModelScanner();
        modelScanner.excludePackage("java");
        FilterableClassScanner classScanner = new FilterableClassScanner();
        classScanner.includePackage("com.wavemaker.tools.apidocs.tools");
        SwaggerConfiguration.Builder builder = new SwaggerConfiguration.Builder("/test", classScanner);
        builder.setClassLoader(this.getClass().getClassLoader());
        builder.setModelScanner(modelScanner);
        addDefaultParameterResolvers(builder);
        SwaggerParser runner = new SpringSwaggerParser(builder.build());
        Swagger swagger = runner.generate();

        writeToFile(swagger, "swagger_with_model_scanner.json");
    }

    @Test
    public void testSingleClass() throws Exception {
        Swagger swagger = runForSingleClass(VacationController.class);
        writeToFile(swagger, "swagger_for_single_class.json");
    }

    @Test
    public void testMultiThread() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        List<Class<?>> controllerClasses = new ArrayList<>();
        controllerClasses.add(VacationController.class);
        controllerClasses.add(UserController.class);
        controllerClasses.add(DepartmentController.class);
        controllerClasses.add(QueryExecutionController.class);

        for (final Class<?> controllerClass : controllerClasses) {
            service.execute(new Runnable() {
                public void run() {
                    Swagger swagger;
                    try {
                        swagger = runForSingleClass(controllerClass);
                    } catch (SwaggerParserException e) {
                        throw new RuntimeException("Exception while parsing class:" + controllerClass.getName(), e);
                    }
                    Assert.assertNotNull(swagger);
                    assertEquals(1, swagger.getTags().size());
                    assertEquals(controllerClass.getName(), swagger.getTags().get(0).getFullyQualifiedName());
                    try {
                        writeToFile(swagger, "class_" + controllerClass.getSimpleName() + ".json");
                    } catch (IOException e) {
                        throw new RuntimeException("Error while writing to file", e);
                    }
                }
            });
        }


        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void testMultiThread2() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        final Class<VacationController> controllerClass = VacationController.class;

        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            service.execute(new Runnable() {
                public void run() {
                    Swagger swagger;
                    try {
                        swagger = runForSingleClass(controllerClass);
                    } catch (SwaggerParserException e) {
                        throw new RuntimeException("Exception while parsing class:" + controllerClass.getName(), e);
                    }
                    Assert.assertNotNull(swagger);
                    assertEquals(1, swagger.getTags().size());
                    assertEquals(controllerClass.getName(), swagger.getTags().get(0).getFullyQualifiedName());
                    try {
                        writeToFile(swagger, "mul_class" + controllerClass.getSimpleName() + "_" + finalI + ".json");
                    } catch (IOException e) {
                        throw new RuntimeException("Error while writing to file", e);
                    }
                }
            });
        }

        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void testMultiThread3() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        List<Class<?>> controllerClasses = new ArrayList<>();
        controllerClasses.add(VacationController.class);
        controllerClasses.add(com.wavemaker.tools.apidocs.tools.spring.controller2.VacationController.class);
        final Pattern namePattern = Pattern.compile("(\\w)*.(\\w*)$");
        for (int i = 0; i < 5; i++) {
            for (final Class<?> controllerClass : controllerClasses) {
                final int finalI = i;
                service.execute(new Runnable() {
                    public void run() {
                        Swagger swagger;
                        try {
                            swagger = runForSingleClass(controllerClass);
                        } catch (SwaggerParserException e) {
                            throw new RuntimeException("Exception while parsing class:" + controllerClass.getName(), e);
                        }
                        Assert.assertNotNull(swagger);
                        assertEquals(1, swagger.getTags().size());
                        assertEquals(controllerClass.getName(),
                                swagger.getTags().get(0).getFullyQualifiedName());
                        try {
                            String name = controllerClass.getName();
                            Matcher nameMatcher = namePattern.matcher(name);
                            if (nameMatcher.find()) {
                                name = nameMatcher.group(0);
                            }
                            name = name.replace('.', '_');

                            writeToFile(swagger,
                                    "mul_package_class_" + name + "_" + finalI + "" +
                                            ".json");
                        } catch (IOException e) {
                            throw new RuntimeException("Error while writing to file", e);
                        }
                    }
                });
            }
        }


        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    private Swagger runForSingleClass(final Class<?> controllerClass) throws SwaggerParserException {
        FilterableModelScanner modelScanner = new FilterableModelScanner();
        modelScanner.excludePackage("java");
        FilterableClassScanner classScanner = new FilterableClassScanner();
        classScanner.includeType(controllerClass);
        SwaggerConfiguration.Builder builder = new SwaggerConfiguration.Builder("/", classScanner);
        builder.setClassLoader(this.getClass().getClassLoader());
        builder.setModelScanner(modelScanner);
        addDefaultParameterResolvers(builder);
        SwaggerParser runner = new SpringSwaggerParser(builder.build());
        return runner.generate();
    }

    private void addDefaultParameterResolvers(final SwaggerConfiguration.Builder builder) {
        builder.addParameterResolver(Pageable.class, new PageParameterResolver());
        builder.addParameterResolver(MultipartFile.class, new MultiPartFileResolver());
        builder.addParameterResolver(MultipartHttpServletRequest.class, new MultiPartRequestResolver());
        builder.addParameterResolver(HttpServletRequest.class, new ServletMetaTypesResolver());
        builder.addParameterResolver(HttpServletResponse.class, new ServletMetaTypesResolver());
    }

    private void writeToFile(final Swagger swagger, final String fileName) throws IOException {
        Assert.assertNotNull(swagger);
        File targetDir = new File("target");
        File outputDir = new File(targetDir, "apidocs");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(outputDir, fileName), swagger);
    }

}
