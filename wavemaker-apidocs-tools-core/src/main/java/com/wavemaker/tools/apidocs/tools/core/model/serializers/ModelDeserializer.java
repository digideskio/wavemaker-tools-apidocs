package com.wavemaker.tools.apidocs.tools.core.model.serializers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.NullNode;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wavemaker.tools.apidocs.tools.core.deserializers.PropertyDeserializer;
import com.wavemaker.tools.apidocs.tools.core.model.*;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 24/3/15 9:03 PM $
 */
public class ModelDeserializer extends StdDeserializer<Model> {
    
    private static final String ARRAY_TYPE = "array";
    private static final String OBJECT_TYPE = "object";
    private static final String REF_TYPE = "ref";
    private static final String COMPOSED_TYPE = "allOf"; //Not sure which field is mandatory. If required we need to check
                                                    //for all the fields.
    
    private static final Map<String, Class<? extends Model>> MODEL_MAPPING = new HashMap<String, Class<? extends Model>>();
    
    static {
        MODEL_MAPPING.put(ARRAY_TYPE, ArrayModel.class);
        MODEL_MAPPING.put(OBJECT_TYPE, ModelImpl.class);
        MODEL_MAPPING.put(REF_TYPE, RefModel.class);
        MODEL_MAPPING.put(COMPOSED_TYPE, ComposedModel.class); //Not sure which field is mandatory. If required we need to check
                                                    //for all the fields.
    }
    
    public ModelDeserializer() {
        super(Model.class);
    }

    @Override
    public Model deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode subType = node.get("type");
        
        ObjectMapper objectMapper = getObjectMapper();

        String subTypeValue;
        if (subType == null || subType instanceof NullNode) {
            if (node.get("$ref") != null) {
                subTypeValue = REF_TYPE;
            } else {
                subTypeValue = OBJECT_TYPE;
            }
        } else {
            subTypeValue = subType.textValue();
        }
        return getModel(node, objectMapper, subTypeValue);
    }

    private Model getModel(JsonNode node, ObjectMapper objectMapper, String modelType) throws IOException {
        return objectMapper.readValue(node.toString(), MODEL_MAPPING.get(modelType));
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        PropertyDeserializer propertyDeserializer = new PropertyDeserializer();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Property.class, propertyDeserializer);
        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }

    public static void main(String[] args) throws IOException {
        String jsonStr = "{\n" +
                "              \"externalDocs\": null,\n" +
                "              \"properties\": null,\n" +
                "              \"type\": \"array\",\n" +
                "              \"description\": null,\n" +
                "              \"items\": {\n" +
                "                \"format\": null,\n" +
                "                \"example\": null,\n" +
                "                \"xml\": null,\n" +
                "                \"position\": null,\n" +
                "                \"description\": null,\n" +
                "                \"title\": null,\n" +
                "                \"readOnly\": null,\n" +
                "                \"default\": null\n" +
                "              },\n" +
                "              \"example\": null\n" +
                "            }";
        ObjectMapper objectMapper = new ObjectMapper();
        ModelDeserializer modelDeserializer = new ModelDeserializer();
        PropertyDeserializer propertyDeserializer = new PropertyDeserializer();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Model.class, modelDeserializer);
        simpleModule.addDeserializer(Property.class, propertyDeserializer);
        objectMapper.registerModule(simpleModule);
        Model model = objectMapper.readValue(jsonStr, Model.class);
        System.out.println(model);
//        ArrayModel arrayModel = new ArrayModel();
//        arrayModel.setDescription("desc");
//        arrayModel.setExample("example");
//        arrayModel.setType("type");
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.valueToTree(arrayModel);
//        System.out.println(jsonNode);
//        ArrayModel arrayModel1 = objectMapper.readValue(jsonNode.toString().getBytes(), ArrayModel.class);
//        System.out.println(arrayModel1);
    }
}
