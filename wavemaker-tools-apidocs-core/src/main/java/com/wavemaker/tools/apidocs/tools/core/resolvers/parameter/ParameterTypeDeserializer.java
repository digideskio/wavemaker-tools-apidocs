package com.wavemaker.tools.apidocs.tools.core.resolvers.parameter;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.BodyParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.CookieParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.HeaderParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.PathParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.QueryParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.RefParameter;
import com.wavemaker.tools.apidocs.tools.core.resolvers.CustomAsTypeDeserializer;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/6/15
 */
public class ParameterTypeDeserializer extends CustomAsTypeDeserializer {

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

    private static final String NULL = "null";

    public ParameterTypeDeserializer(
            final JavaType bt, final TypeIdResolver idRes, final String typePropertyName,
            final boolean typeIdVisible, final Class<?> defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public ParameterTypeDeserializer(final AsPropertyTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    @Override
    protected Class<?> findSubType(final JsonNode jsonNode) {
        Class<?> subType = null;
        if (jsonNode.get("$ref") != null) {
            String ref = jsonNode.get("$ref").asText();
            if (ref != null && !ref.equals(NULL)) {
                subType = RefParameter.class;
            }
        }

        if (subType == null && jsonNode.get("in") != null) { // it won't come
            String type = jsonNode.get("in").asText();
            subType = subTypesMap.get(type);
        } else {
            throw new IllegalStateException("Parameter must contain 'in' field:" + jsonNode);
        }

        return subType;
    }

    @Override
    protected TypeDeserializer newInstance(final BeanProperty property) {
        return new ParameterTypeDeserializer(this, property);
    }
}
