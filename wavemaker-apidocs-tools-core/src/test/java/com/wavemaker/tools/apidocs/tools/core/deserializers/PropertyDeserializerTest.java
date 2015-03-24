package com.wavemaker.tools.apidocs.tools.core.deserializers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 24/3/15
 */
public class PropertyDeserializerTest {
    private static final String TEST_JSON_FILE = "/test_properties.json";

    private ObjectMapper objectMapper;

    public PropertyDeserializerTest() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void deserializerTest() throws IOException {
        TypeReference<Map<String, Property>> propertyTypeReference = new TypeReference<Map<String, Property>>() {
        };
        InputStream inputStream = this.getClass().getResourceAsStream(TEST_JSON_FILE);
        if (inputStream != null) {
            Map<String, Property> propertyMap = objectMapper.readValue(inputStream,
                    propertyTypeReference);
            Assert.assertNotNull(propertyMap);
            objectMapper.writeValue(new File("target", "test_properties.json"), propertyMap);
        } else {
            System.err.println("File not found");
        }
    }
}
