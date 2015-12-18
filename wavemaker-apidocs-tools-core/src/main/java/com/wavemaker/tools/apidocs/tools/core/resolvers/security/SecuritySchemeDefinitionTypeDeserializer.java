package com.wavemaker.tools.apidocs.tools.core.resolvers.security;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.wavemaker.tools.apidocs.tools.core.model.auth.ApiKeyAuthDefinition;
import com.wavemaker.tools.apidocs.tools.core.model.auth.BasicAuthDefinition;
import com.wavemaker.tools.apidocs.tools.core.model.auth.OAuth2Definition;
import com.wavemaker.tools.apidocs.tools.core.model.auth.SecuritySchemeDefinition;
import com.wavemaker.tools.apidocs.tools.core.resolvers.CustomAsTypeDeserializer;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 18/12/15
 */
public class SecuritySchemeDefinitionTypeDeserializer extends CustomAsTypeDeserializer {

    private static final Map<String, Class<? extends SecuritySchemeDefinition>> subTypesMap = new HashMap<>();

    private static final String keyList;

    static {
        subTypesMap.put("basic", BasicAuthDefinition.class);
        subTypesMap.put("apiKey", ApiKeyAuthDefinition.class);
        subTypesMap.put("oauth2", OAuth2Definition.class);

        keyList = "[basic, apiKey, oauth2]";
    }


    public SecuritySchemeDefinitionTypeDeserializer(
            final JavaType bt,
            final TypeIdResolver idRes, final String typePropertyName,
            final boolean typeIdVisible, final Class<?> defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public SecuritySchemeDefinitionTypeDeserializer(final AsPropertyTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    @Override
    protected Class<?> findSubType(final JsonNode jsonNode) {
        if (jsonNode.get("type") != null) {
            String type = jsonNode.get("type").asText();
            if (subTypesMap.containsKey(type)) {
                return subTypesMap.get(type);
            } else {
                throw new IllegalStateException("SecuritySchemaDefinition's 'type' must be in " + keyList + " for " +
                        "field:" + jsonNode);
            }
        } else {
            throw new IllegalStateException("SecuritySchemaDefinition must contain 'type' field:" + jsonNode);
        }
    }

    @Override
    protected TypeDeserializer newInstance(final BeanProperty property) {
        return new SecuritySchemeDefinitionTypeDeserializer(this, property);
    }
}
