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
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.*;
import com.wavemaker.tools.apidocs.tools.core.model.properties.*;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 24/3/15
 */
public class ParameterDeserializer extends StdDeserializer<Parameter> {
    private static final Map<String, Class<? extends Parameter>> subTypesMap = new HashMap<>();

    /*
    @JsonSubTypes({	@Type(value = BodyParameter.class, name = "body"),
//				@Type(value = CookieParameter.class, name = "cookie"),
//				@Type(value = PathParameter.class, name = "path"),
//				@Type(value = FormParameter.class, name = "formData"),
//				@Type(value = HeaderParameter.class, name = "header"),
//				@Type(value = QueryParameter.class, name = "query"),
//				@Type(value = RefParameter.class, name = "ref")
//			})
     */

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
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ModelDeserializer modelDeserializer = new ModelDeserializer();
        PropertyDeserializer propertyDeserializer = new PropertyDeserializer();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Model.class, modelDeserializer);
        simpleModule.addDeserializer(Property.class, propertyDeserializer);
        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }
}
