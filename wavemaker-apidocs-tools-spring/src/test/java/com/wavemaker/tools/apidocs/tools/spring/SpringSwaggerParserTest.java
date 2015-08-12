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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wavemaker.tools.apidocs.tools.core.model.Info;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;
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

public class SpringSwaggerParserTest {

    @Test
    public void testGenerate() throws Exception {
        FilterableClassScanner classScanner = new FilterableClassScanner();
        classScanner.includePackage("com.wavemaker.tools.apidocs.tools");
        Info info = new Info();
        info.description("hrdb database service apis");
        info.setTitle("HRDB service");
        SwaggerConfiguration.Builder builder = new SwaggerConfiguration.Builder("/test", classScanner);
        builder.setInfo(info);
        builder.setClassLoader(this.getClass().getClassLoader());
        SwaggerParser runner = new SpringSwaggerParser(builder.build());
        Swagger swagger = runner.generate();

        writeToFile(swagger, "swagger.json");
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
                    Assert.assertEquals(1, swagger.getTags().size());
                    Assert.assertEquals(controllerClass.getName(), swagger.getTags().get(0).getFullyQualifiedName());
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
