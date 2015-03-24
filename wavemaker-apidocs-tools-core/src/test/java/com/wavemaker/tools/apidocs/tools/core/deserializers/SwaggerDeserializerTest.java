package com.wavemaker.tools.apidocs.tools.core.deserializers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 12:20 AM $
 */
public class SwaggerDeserializerTest {

    private static final String TEST_JSON_FILE = "/test_swagger.json";

    private ObjectMapper objectMapper;

    public SwaggerDeserializerTest() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void deserializerTest() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(TEST_JSON_FILE);
        if (inputStream != null) {
            Swagger swagger= objectMapper.readValue(inputStream, Swagger.class);
            Assert.assertNotNull(swagger);
            objectMapper.writeValue(new File("/home/nishanth/Desktop", "test_swwagger.json"), swagger);
            System.out.println(swagger);
//            objectMapper.writeValue(new File("target", "test_properties.json"), property);
        } else {
            System.err.println("File not found");
        }
    }
}
