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
package com.wavemaker.tools.apidocs.tools.spring;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wavemaker.tools.apidocs.tools.core.model.Info;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;
import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.runner.SwaggerParser;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableClassScanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 14/11/16
 */
public class ReturnsAsFeatureTest {

    @Test
    public void testGenerate() throws Exception {
        FilterableClassScanner classScanner = new FilterableClassScanner();
        final String packageName = "com.test.returnsas";
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
        assertEquals(1, swagger.getTags().size());
        writeToFile(swagger, "swagger_returnsas.json");
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
