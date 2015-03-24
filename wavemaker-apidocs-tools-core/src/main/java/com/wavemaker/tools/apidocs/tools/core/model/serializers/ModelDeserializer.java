package com.wavemaker.tools.apidocs.tools.core.model.serializers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wavemaker.tools.apidocs.tools.core.model.*;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 24/3/15 9:03 PM $
 */
public class ModelDeserializer extends JsonDeserializer<Model> {
    
    private static final String ARRAY_TYPE = "array";
    private static final String OBJECT_TYPE = "object";
    private static final String REF_TYPE = "$ref";
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
    
    @Override
    public Model deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode node1 = node.get("type");
        ObjectMapper objectMapper = new ObjectMapper();
//        if (node.has(ARRAY_TYPE)) {
            return getModel(jsonParser, node, objectMapper, ARRAY_TYPE);
//        } else if (node.has(OBJECT_TYPE)) {
//            return getModel(node, objectMapper, OBJECT_TYPE);
//        } else if (node.has(REF_TYPE)) {
//            return getModel(node, objectMapper, REF_TYPE);
//        } else if (node.has(COMPOSED_TYPE)) {
//            return getModel(node, objectMapper, COMPOSED_TYPE);
        }
        
//        return null;
//    }

    private Model getModel(JsonParser jsonParser, JsonNode node, ObjectMapper objectMapper, String modelType) throws IOException {
        Iterator<ArrayModel> arrayModelIterator = jsonParser.readValuesAs(ArrayModel.class);
        String json = node.get(modelType).asText();
        return objectMapper.readValue(node.toString(), MODEL_MAPPING.get(modelType));
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
        Model model = new ObjectMapper().readValue(jsonStr, Model.class);
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
