/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 30/3/15
 */
public abstract class ExtendedStdDeserializer<T> extends StdDeserializer<T> {


    public ExtendedStdDeserializer(final Class<T> vc) {
        super(vc);
    }

    @Override
    public T deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException {
        final ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();

        final ObjectNode objectNode = mapper.readTree(jsonParser);

        final Class<? extends T> subType = findSubType(objectNode);

        if (subType == null) {
            throw ctxt.missingTypeIdException(ctxt.constructType(_valueClass), "Unable to find sub type");
        }

        return mapper.treeToValue(objectNode, subType);
    }

    protected abstract Class<? extends T> findSubType(ObjectNode objectNode);

}
