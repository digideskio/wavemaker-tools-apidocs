/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring;

import java.io.File;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.io.Files;
import com.google.gson.GsonBuilder;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.Swagger;
import com.wavemaker.tools.apidocs.tools.parser.config.ApiParserConfigurationBuilder;
import com.wavemaker.tools.apidocs.tools.parser.runner.SwaggerParser;
import com.wavemaker.tools.apidocs.tools.parser.scanner.PackageClassScanner;

public class SpringSwaggerParserTest {

    @Test
    public void testGenerate() throws Exception {
        ApiParserConfigurationBuilder builder = new ApiParserConfigurationBuilder();
//        testHelper(builder, "com.test1", "apidocs");
        testHelper(builder, "com.wavemaker.tools.apidocs.tools.spring", "apidocs");
//        testHelper(builder, "com.wavemaker.test.controllers", "test");

    }

    public void testHelper(
            ApiParserConfigurationBuilder builder, String packageName,
            String outDir) throws Exception {
        builder.setClassScanner(new PackageClassScanner(packageName));
        builder.addExcludeModelPackage("java");
        builder.setExcludeSubPackages(true);
        builder.setDefaultThreadPool();
        SwaggerParser runner = new SpringSwaggerParser(builder.build());
        Swagger swagger = runner.generate();

        Assert.assertNotNull(swagger);
        File targetDir = new File("target");
        File outputDir = new File(targetDir, outDir);
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        Files.write(new GsonBuilder().setPrettyPrinting().create().toJson(swagger), new File(outputDir,
                        "swagger.json"),
                Charset.defaultCharset());
    }

}
