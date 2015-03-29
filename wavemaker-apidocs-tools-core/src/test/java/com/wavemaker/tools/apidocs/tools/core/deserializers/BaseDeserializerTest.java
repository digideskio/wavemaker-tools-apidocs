package com.wavemaker.tools.apidocs.tools.core.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 7:22 PM $
 */
public class BaseDeserializerTest {

    protected ObjectMapper objectMapper;

    public BaseDeserializerTest() {
        objectMapper = new ObjectMapper();
        ParameterDeserializer parameterDeserializer = new ParameterDeserializer();
        
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Parameter.class, parameterDeserializer);
        objectMapper.registerModule(simpleModule);
        
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
}
