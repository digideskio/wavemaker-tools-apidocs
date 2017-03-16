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
package com.wavemaker.tools.apidocs.tools.core.resolvers.constraint;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.wavemaker.tools.apidocs.tools.core.model.Constraint;
import com.wavemaker.tools.apidocs.tools.core.model.constraint.BasicConstraint;
import com.wavemaker.tools.apidocs.tools.core.model.constraint.RefConstraint;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.RefParameter;
import com.wavemaker.tools.apidocs.tools.core.resolvers.CustomAsTypeDeserializer;

/**
 * @author Uday Shankar
 */
public class ConstraintTypeDeserializer extends CustomAsTypeDeserializer {

    private static final Map<String, Class<? extends Constraint>> subTypesMap = new HashMap<>();

    static {
        subTypesMap.put("basic", BasicConstraint.class);
        subTypesMap.put("ref", RefConstraint.class);
    }

    private static final String NULL = "null";

    public ConstraintTypeDeserializer(
            final JavaType bt, final TypeIdResolver idRes, final String typePropertyName,
            final boolean typeIdVisible, final Class<?> defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public ConstraintTypeDeserializer(final AsPropertyTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    @Override
    protected Class<?> findSubType(final JsonNode jsonNode) {
        Class<?> subType = null;
        if (jsonNode.get("type") != null) { // it won't come
            String type = jsonNode.get("type").asText();
            subType = subTypesMap.get(type);
        } else {
            subType = BasicConstraint.class;
        }

        return subType;
    }

    @Override
    protected TypeDeserializer newInstance(final BeanProperty property) {
        return new ConstraintTypeDeserializer(this, property);
    }
}
