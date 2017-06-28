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
package com.wavemaker.tools.apidocs.tools.core.deserializers;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.flipkart.zjsonpatch.JsonDiff;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 7:22 PM $
 */
public class BaseDeserializerTest {

    protected ObjectMapper objectMapper;

    public BaseDeserializerTest() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    protected <T> T test(InputStream is, TypeReference<T> reference) throws IOException {
        return test(is, (jsonParser -> objectMapper.readValue(jsonParser, reference)));
    }

    protected <T> T test(InputStream is, Class<T> type) throws IOException {
        return test(is, (jsonParser -> objectMapper.readValue(jsonParser, type)));
    }

    protected <T> T test(InputStream is, ConverterFunction<T> converter) throws IOException {
        final JsonNode jsonNodeExpected = objectMapper.readTree(is);

        T object = converter.convert(objectMapper.treeAsTokens(jsonNodeExpected));
        assertNotNull(object);

        final JsonNode jsonNode = JsonDiff.asJson(jsonNodeExpected, objectMapper.valueToTree(object));
        assertTrue(jsonNode.toString(), jsonNode.size() == 0);

        return object;
    }

    @FunctionalInterface
    public interface ConverterFunction<R> {
        R convert(JsonParser jsonParser) throws IOException;
    }


}
