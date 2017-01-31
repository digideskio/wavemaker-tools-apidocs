/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tools.apidocs.tools.core.resolvers.property;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.wavemaker.tools.apidocs.tools.core.model.properties.*;
import com.wavemaker.tools.apidocs.tools.core.resolvers.CustomAsTypeDeserializer;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 26/3/15
 */
public class PropertyTypeDeserializer extends CustomAsTypeDeserializer {

    private static final Map<String, Class<? extends Property>> subTypesMap = new HashMap<>();

    static {
        subTypesMap.put("boolean", BooleanProperty.class);
        subTypesMap.put("date", DateProperty.class);
        subTypesMap.put("date-time", DateTimeProperty.class);
        subTypesMap.put("number", DecimalProperty.class);
        subTypesMap.put("double", DoubleProperty.class);
        subTypesMap.put("float", FloatProperty.class);
        subTypesMap.put("int32", IntegerProperty.class);
        subTypesMap.put("int64", LongProperty.class);
        subTypesMap.put("file", FileProperty.class);
        subTypesMap.put("map", MapProperty.class);
        subTypesMap.put("object", ObjectProperty.class);
        subTypesMap.put("array", ArrayProperty.class);
        subTypesMap.put("string", StringProperty.class);
    }

    public static final String NULL = "null";

    public PropertyTypeDeserializer(final PropertyTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    public PropertyTypeDeserializer(
            final JavaType bt, final TypeIdResolver idRes,
            final String typePropertyName, final boolean typeIdVisible, final Class<?> defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    @Override
    protected Class<?> findSubType(JsonNode node) {
        Class<? extends Property> subType = null;
        if (node.get("format") != null) {
            String format = node.get("format").asText();
            if (format != null && !format.equals(NULL)) {
                subType = subTypesMap.get(format);
            }
        }
        if (subType == null) {
            if (node.get("type") != null) { // it won't come
                String type = node.get("type").asText();
                if (type != null && !type.equals(NULL)) {
                    if (type.equals("object")) {
                        if (node.get("additionalProperties") != null) {
                            subType = MapProperty.class;
                        } else {
                            subType = ObjectProperty.class;
                        }
                    } else {
                        subType = subTypesMap.get(type);
                    }
                }
            }
        }
        if (subType == null) {
            if (node.get("$ref") != null) {
                String ref = node.get("$ref").asText();
                if (ref != null && !ref.equals(NULL)) {
                    subType = RefProperty.class;
                }
            }
        }
        if (subType == null) {
            subType = ObjectProperty.class; // default one
        }

        return subType;
    }

    @Override
    protected TypeDeserializer newInstance(final BeanProperty property) {
        return new PropertyTypeDeserializer(this, property);
    }
}
