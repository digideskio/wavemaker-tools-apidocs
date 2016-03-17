/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
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
package com.wavemaker.tools.apidocs.tools.core.resolvers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.SimpleType;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 30/3/15
 */
public abstract class CustomAsTypeDeserializer extends AsPropertyTypeDeserializer {
    public CustomAsTypeDeserializer(
            final JavaType bt, final TypeIdResolver idRes,
            final String typePropertyName, final boolean typeIdVisible, final Class<?> defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public CustomAsTypeDeserializer(final AsPropertyTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    @Override
    public final TypeDeserializer forProperty(final BeanProperty prop) {
        return (prop == _property) ? this : newInstance(prop);
    }

    @Override
    public final Object deserializeTypedFromObject(
            final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.readValueAsTree();
        Class<?> subType = findSubType(node);
        JavaType type = SimpleType.construct(subType);

        JsonParser jsonParser = new TreeTraversingParser(node, jp.getCodec());
        if (jsonParser.getCurrentToken() == null) {
            jsonParser.nextToken();
        }
        /* 16-Dec-2010, tatu: Since nominal type we get here has no (generic) type parameters,
        *   we actually now need to explicitly narrow from base type (which may have parameterization)
        *   using raw type.
        *
        *   One complication, though; can not change 'type class' (simple type to container); otherwise
        *   we may try to narrow a SimpleType (Object.class) into MapType (Map.class), losing actual
        *   type in process (getting SimpleType of Map.class which will not work as expected)
        */
        if (_baseType != null && _baseType.getClass() == type.getClass()) {
            type = _baseType.narrowBy(type.getRawClass());
        }
        JsonDeserializer<Object> deser = ctxt.findContextualValueDeserializer(type, _property);
        return deser.deserialize(jsonParser, ctxt);
    }

    protected abstract Class<?> findSubType(JsonNode jsonNode);

    protected abstract TypeDeserializer newInstance(BeanProperty property);
}
