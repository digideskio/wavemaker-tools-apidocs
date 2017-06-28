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
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wavemaker.tools.apidocs.tools.core.model.Model;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 11:30 PM $
 */
public class ModelDeserializerTest extends BaseDeserializerTest {
    private static final String TEST_JSON_FILE = "/test_model.json";

    public ModelDeserializerTest() {
        super();
    }

    @Test
    public void deserializerTest() throws IOException {
        TypeReference<Map<String, Model>> modelTypeReference = new TypeReference<Map<String, Model>>() {
        };
        InputStream inputStream = this.getClass().getResourceAsStream(TEST_JSON_FILE);
        test(inputStream, modelTypeReference);
    }

}
