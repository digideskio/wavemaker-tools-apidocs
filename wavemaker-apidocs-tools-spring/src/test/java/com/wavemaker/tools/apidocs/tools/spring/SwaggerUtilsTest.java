package com.wavemaker.tools.apidocs.tools.spring;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wavemaker.tools.apidocs.tools.core.model.Info;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;
import com.wavemaker.tools.apidocs.tools.core.utils.SwaggerUtils;
import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.runner.SwaggerParser;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableClassScanner;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableModelScanner;
import com.wavemaker.tools.apidocs.tools.spring.controller.DepartmentController;
import com.wavemaker.tools.apidocs.tools.spring.controller.EmployeeController;

/**
 * Created by sunilp on 21/5/15.
 */
public class SwaggerUtilsTest {

    @Test
    public void mergeSwagger() throws Exception
    {
        FilterableClassScanner classScannerOne = new FilterableClassScanner();
        classScannerOne.include(FilterableClassScanner.prefix(DepartmentController.class.getName()));
        FilterableModelScanner modelScannerOne = new FilterableModelScanner();
        modelScannerOne.excludePackage("java");
        Info infoOne = new Info();
        infoOne.description("hrdb database service apis");
        infoOne.setTitle("HRDB service");
        SwaggerConfiguration.Builder builderOne = new SwaggerConfiguration.Builder("/test", classScannerOne);
        builderOne.setInfo(infoOne);
        builderOne.setModelScanner(modelScannerOne);
        builderOne.setClassLoader(this.getClass().getClassLoader());
        SwaggerParser runnerOne = new SpringSwaggerParser(builderOne.build());
        Swagger swaggerOne = runnerOne.generate();
        Assert.assertNotNull(swaggerOne);

        FilterableClassScanner classScannerTwo = new FilterableClassScanner();
        classScannerTwo.include(FilterableClassScanner.prefix(EmployeeController.class.getName()));
        FilterableModelScanner modelScannerTwo = new FilterableModelScanner();
        modelScannerTwo.excludePackage("java");
        Info infoTwo = new Info();
        infoTwo.description("hrdb database service apis");
        infoTwo.setTitle("HRDB service");
        SwaggerConfiguration.Builder builderTwo = new SwaggerConfiguration.Builder("/test", classScannerTwo);
        builderTwo.setInfo(infoTwo);
        builderTwo.setModelScanner(modelScannerTwo);
        builderTwo.setClassLoader(this.getClass().getClassLoader());
        SwaggerParser runnerTwo = new SpringSwaggerParser(builderTwo.build());
        Swagger swaggerTwo = runnerTwo.generate();
        Assert.assertNotNull(swaggerTwo);


        Swagger finalSwagger = SwaggerUtils.addOrUpdate(swaggerOne,swaggerTwo);
        Assert.assertNotNull(finalSwagger);

        File targetDir = new File("target");
        File outputDir = new File(targetDir, "apidocs");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(outputDir, "swagger.json"), finalSwagger);
    }
}
