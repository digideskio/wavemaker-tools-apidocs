package com.wavemaker.tools.apidocs.tools.core.deserializers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 7:22 PM $
 */
public class BaseDeserializerTest {
    
    protected ObjectMapper objectMapper;
    
    public BaseDeserializerTest() {
        objectMapper = new ObjectMapper();
        
        ModelDeserializer modelDeserializer = new ModelDeserializer();
        PropertyDeserializer propertyDeserializer = new PropertyDeserializer();
        ParameterDeserializer parameterDeserializer = new ParameterDeserializer();
        
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Model.class, modelDeserializer);
        simpleModule.addDeserializer(Property.class, propertyDeserializer);
        simpleModule.addDeserializer(Parameter.class, parameterDeserializer);
        
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.registerModule(simpleModule);
    }
}
