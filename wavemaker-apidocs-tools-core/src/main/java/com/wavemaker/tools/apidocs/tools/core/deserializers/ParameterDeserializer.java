package com.wavemaker.tools.apidocs.tools.core.deserializers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.*;
import com.wavemaker.tools.apidocs.tools.core.model.properties.*;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 8:13 PM $
 */
public class ParameterDeserializer extends StdDeserializer<Parameter> {
    private static final Map<String, Class<? extends Parameter>> subTypesMap = new HashMap<>();

    static {
        subTypesMap.put("body", BodyParameter.class);
        subTypesMap.put("cookie", CookieParameter.class);
        subTypesMap.put("path", PathParameter.class);
        subTypesMap.put("formData", FormParameter.class);
        subTypesMap.put("header", HeaderParameter.class);
        subTypesMap.put("query", QueryParameter.class);
        subTypesMap.put("ref", RefParameter.class);
    }

    public ParameterDeserializer() {
        super(Parameter.class);
    }

    @Override
    public Parameter deserialize(
            final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jp.readValueAsTree();
        Class<? extends Parameter> subType = null;

        if (jsonNode.get("in") != null) { // it won't come
            String type = jsonNode.get("in").asText();
            subType = subTypesMap.get(type);
        } else {
            subType = RefParameter.class;
        }
        
        return getObjectMapper().readValue(jsonNode.toString(), subType);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
}
