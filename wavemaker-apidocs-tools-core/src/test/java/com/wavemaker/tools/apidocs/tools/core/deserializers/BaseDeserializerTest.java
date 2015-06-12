package com.wavemaker.tools.apidocs.tools.core.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 7:22 PM $
 */
public class BaseDeserializerTest {

    protected ObjectMapper objectMapper;

    public BaseDeserializerTest() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
}
