/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.spring;

import java.io.File;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.io.Files;
import com.google.gson.GsonBuilder;
import com.wavemaker.tools.api.core.config.ApiParserConfigurationBuilder;
import com.wavemaker.tools.api.core.models.ApiDocument;
import com.wavemaker.tools.api.core.models.ApiDocuments;
import com.wavemaker.tools.api.core.runner.ApiParserRunner;
import com.wavemaker.tools.api.core.scanners.PackageClassScanner;

public class SpringApiParserRunnerTest {

    @Test
    public void testGenerate() throws Exception {
        ApiParserConfigurationBuilder builder = new ApiParserConfigurationBuilder();
//        testHelper(builder, "com.test1", "apidocs");
        testHelper(builder, "com.wavemaker.tools.api.spring.test", "apidocs");
//        testHelper(builder, "com.wavemaker.test.controllers", "test");

    }

    public void testHelper(ApiParserConfigurationBuilder builder, String packageName,
            String outDir) throws Exception {
        builder.setClassScanner(new PackageClassScanner(packageName));
        builder.addExcludeModelPackage("java");
        builder.setExcludeSubPackages(true);
        builder.setDefaultThreadPool();
        ApiParserRunner runner = new SpringApiParserRunner(builder.build());
        ApiDocuments documents = runner.generate();

        Assert.assertNotNull(documents);
        File targetDir = new File("target");
        File outputDir = new File(targetDir, outDir);
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        for (ApiDocument document : documents.getDocumentsAsList()) {
            Files.write(new GsonBuilder().setPrettyPrinting().create().toJson(document), new File(outputDir,
                            document.getName() + ".json"),
                    Charset.defaultCharset());
        }
        System.out.println("File created at:" + outputDir.getCanonicalPath());
    }

}