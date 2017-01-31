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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;

import static junit.framework.TestCase.assertNotNull;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 24/3/15 11:30 PM $
 */
public class ParameterDeserializerTest extends BaseDeserializerTest {

    private static final String TEST_JSON_FILE = "/test_parameter.json";

    public ParameterDeserializerTest() {
        super();
    }

    @Test
    public void deserializerTest() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(
                TEST_JSON_FILE);
        Parameter parameter = objectMapper.readValue(inputStream,
                Parameter.class);
	    assertNotNull(parameter);
        objectMapper.writeValue(new File("target", "test_parameters.json"), parameter);
    }
}
