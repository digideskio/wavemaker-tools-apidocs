package com.wavemaker.tools.apidocs.tools.core.deserializers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.BodyParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.PathParameter;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 24/3/15 11:30 PM $
 */
public class ParameterDeserializerTest extends BaseDeserializerTest {

    private static final String TEST_JSON_FILE = "/test_parameter.json";

    public ParameterDeserializerTest() {
        super();
    }
    
    @Test
    public void deserializerTest() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(TEST_JSON_FILE);
        Parameter parameter = objectMapper.readValue(inputStream, Parameter.class);
        System.out.println("->" + parameter);
        objectMapper.writeValue(new File("/home/nishanth/Desktop", "test_parameters.json"), parameter);
    }
}
