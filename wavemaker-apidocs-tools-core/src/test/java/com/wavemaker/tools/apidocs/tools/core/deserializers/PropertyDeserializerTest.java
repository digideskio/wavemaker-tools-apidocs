package com.wavemaker.tools.apidocs.tools.core.deserializers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wavemaker.tools.apidocs.tools.core.model.properties.ArrayProperty;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.core.model.properties.StringProperty;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 24/3/15
 */
public class PropertyDeserializerTest extends BaseDeserializerTest {
    private static final String TEST_JSON_FILE = "/test_properties.json";

    public PropertyDeserializerTest() {
        super();
    }

    @Test
    public void deserializerTest() throws IOException {
        TypeReference<Map<String, Property>> propertyTypeReference = new TypeReference<Map<String, Property>>() {
        };
        InputStream inputStream = this.getClass().getResourceAsStream(TEST_JSON_FILE);
        if (inputStream != null) {
            Map<String, Property> propertyMap= objectMapper.readValue(inputStream, propertyTypeReference);
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            Assert.assertNotNull(propertyMap);
            objectMapper.writeValue(new File("target", "test_propert.json"), propertyMap);
            System.out.println(propertyMap);
//            objectMapper.writeValue(new File("target", "test_properties.json"), property);
        } else {
            System.err.println("File not found");
        }
    }
    
    @Test
    public void serializationTest() {
        ArrayProperty arrayProperty = new ArrayProperty();
        arrayProperty.setDescription("desc");
        arrayProperty.setExample("exam");
        arrayProperty.setName("arrayprop");

        StringProperty items = new StringProperty();
        items.setName("stringprop");
        items.setExample("stringexamp");
        items.setDescription("stirng edsc");

        arrayProperty.setItems(items);

        JsonNode jsonNode = objectMapper.valueToTree(arrayProperty);
        System.out.println("->" + jsonNode.toString());
    }
}
