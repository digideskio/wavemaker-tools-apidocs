/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;
import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.runner.SwaggerParser;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableClassScanner;
import com.wavemaker.tools.apidocs.tools.spring.resolver.MultiPartFileResolver;
import com.wavemaker.tools.apidocs.tools.spring.resolver.MultiPartRequestResolver;
import com.wavemaker.tools.apidocs.tools.spring.resolver.PageParameterResolver;

public class SpringSwaggerParserTest {

    @Test
    public void testGenerate() throws Exception {
        FilterableClassScanner classScanner = new FilterableClassScanner();
        classScanner.includePackage("com.wavemaker.tools.apidocs.tools");
        SwaggerConfiguration.Builder builder = new SwaggerConfiguration.Builder("Service 1", "/test", classScanner);
        builder.setClassLoader(this.getClass().getClassLoader());
        builder.addExcludeModelPackage("java");
        SwaggerParser runner = new SpringSwaggerParser(builder.build());
        Swagger swagger = runner.generate();

        Assert.assertNotNull(swagger);
        File targetDir = new File("target");
        File outputDir = new File(targetDir, "apidocs");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(outputDir, "swagger.json"), swagger);
       /* Files.write(;, new File(outputDir,
                        "swagger.json"),
                Charset.defaultCharset());*/
    }

    @Test
    public void testGenerateWithResolvers() throws Exception {
        FilterableClassScanner classScanner = new FilterableClassScanner();
        classScanner.includePackage("com.wavemaker.tools.apidocs.tools");
        SwaggerConfiguration.Builder builder = new SwaggerConfiguration.Builder("HRDB Service", "/test", classScanner);
        builder.setClassLoader(this.getClass().getClassLoader());
        builder.addExcludeModelPackage("java");
        builder.addParameterResolver(Pageable.class, new PageParameterResolver());
        builder.addParameterResolver(MultipartFile.class, MultiPartFileResolver.getInstance());
        builder.addParameterResolver(MultipartHttpServletRequest.class, MultiPartRequestResolver.getInstance());
        SwaggerParser runner = new SpringSwaggerParser(builder.build());
        Swagger swagger = runner.generate();

        Assert.assertNotNull(swagger);
        File targetDir = new File("target");
        File outputDir = new File(targetDir, "apidocs");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(outputDir, "swagger_with_resolvers.json"), swagger);
       /* Files.write(;, new File(outputDir,
                        "swagger.json"),
                Charset.defaultCharset());*/
    }

}
