package com.wavemaker.tools.apidocs.tools.core.deserializers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.core.model.properties.RefProperty;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 12:20 AM $
 */
public class SwaggerDeserializerTest extends BaseDeserializerTest {

    private static final String TEST_JSON_FILE = "/test_swagger.json";

    public SwaggerDeserializerTest() {
        super();
    }

    @Test
    public void deserializerTest() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(TEST_JSON_FILE);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Swagger swagger = objectMapper.readValue(inputStream, Swagger.class);
        Assert.assertNotNull(swagger);
        objectMapper.writeValue(new File("target", "test_swagger.json"), swagger);
    }

    @Test
    public void deserializerTypeArgsTest() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/test_swagger_type_args.json");
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Swagger swagger = objectMapper.readValue(inputStream, Swagger.class);
        Assert.assertNotNull(swagger);
        File outputFile = new File("target", "test_swagger_type_args.json");
        objectMapper.writeValue(outputFile, swagger);

        Property schema = swagger.getPath("/hrdb/Department/").getGet().getResponses().get("200").getSchema();
        Assert.assertNotNull(schema);
        Assert.assertNotNull(((RefProperty) schema).getTypeArguments());
    }

    @Test
    public void deserializerWeatherApiTest() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/weather_api.json");
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Swagger swagger = objectMapper.readValue(inputStream, Swagger.class);
        Assert.assertNotNull(swagger);
        objectMapper.writeValue(new File("target", "weather_api.json"), swagger);
    }
}
