package com.wavemaker.tools.apidocs.tools.core.deserializers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.properties.*;
import com.wavemaker.tools.apidocs.tools.core.model.serializers.ModelDeserializer;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 24/3/15
 */
public class PropertyDeserializer extends StdDeserializer<Property> {
    private static final Map<String, Class<? extends Property>> subTypesMap = new HashMap<>();

    static {
        subTypesMap.put("object", ObjectProperty.class);
        subTypesMap.put("array", ArrayProperty.class);
        subTypesMap.put("boolean", BooleanProperty.class);
        subTypesMap.put("date", DateProperty.class);
        subTypesMap.put("date-time", DateTimeProperty.class);
        subTypesMap.put("number", DecimalProperty.class);
        subTypesMap.put("double", DoubleProperty.class);
        subTypesMap.put("float", FloatProperty.class);
        subTypesMap.put("int32", IntegerProperty.class);
        subTypesMap.put("int64", LongProperty.class);
        subTypesMap.put("file", FileProperty.class);
        subTypesMap.put("ref", RefProperty.class);
        subTypesMap.put("string", StringProperty.class);
        subTypesMap.put("uuid", UUIDProperty.class);
    }

    public PropertyDeserializer() {
        super(Property.class);
    }


    @Override
    public Property deserialize(
            final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jp.readValueAsTree();// jp.getCode().readTree();
//        JsonNode jsonNode = jp.getCodec().readTree(jp);// jp.getCode().readTree();
        Class<? extends Property> subType = null;
        if (jsonNode.get("format") != null) {
            String format = jsonNode.get("format").asText();
            subType = subTypesMap.get(format);
        }
        if (subType == null) {
            if (jsonNode.get("type") != null) { // it won't come
                String type = jsonNode.get("type").asText();
                if (type.equals("object")) {
                    if (jsonNode.get("additionalProperties") != null) {
                        subType = MapProperty.class;
                    } else {
                        subType = ObjectProperty.class;
                    }
                } else {
                    subType = subTypesMap.get(type);
                }
            }
        }

        if (subType == null) {
            if (jsonNode.asText().isEmpty()) {
                subType = RefProperty.class;
            } else {
                subType = ObjectProperty.class; // default one
            }
        }
        ObjectMapper objectMapper;
        if (subType == MapProperty.class || subType == ArrayProperty.class) {
            objectMapper = new ObjectMapper();
            ModelDeserializer modelDeserializer = new ModelDeserializer();
            PropertyDeserializer propertyDeserializer = new PropertyDeserializer();
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addDeserializer(Model.class, modelDeserializer);
            simpleModule.addDeserializer(Property.class, propertyDeserializer);
            objectMapper.registerModule(simpleModule);
        } else {
            objectMapper = getObjectMapper();
        }
        return objectMapper.readValue(jsonNode.toString(), subType);
    }
    
    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        ModelDeserializer modelDeserializer = new ModelDeserializer();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Model.class, modelDeserializer);
        objectMapper.registerModule(simpleModule);
        
        return objectMapper;
    }
}
