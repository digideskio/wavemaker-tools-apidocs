package com.wavemaker.tools.apidocs.tools.core.deserializers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wavemaker.tools.apidocs.tools.core.model.Model;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 11:30 PM $
 */
public class ModelDeserializerTest extends BaseDeserializerTest {
    private static final String TEST_JSON_FILE = "/test_model.json";

    public ModelDeserializerTest() {
        super();
    }

    @Test
    public void deserializerTest() throws IOException {
        TypeReference<Map<String, Model>> propertyTypeReference = new TypeReference<Map<String, Model>>() {
        };
        InputStream inputStream = this.getClass().getResourceAsStream(TEST_JSON_FILE);
        Map<String, Model> modelMap = objectMapper.readValue(inputStream, propertyTypeReference);
        Assert.assertNotNull(modelMap);
        objectMapper.writeValue(new File("target", "test_model.json"), modelMap);
    }

}
