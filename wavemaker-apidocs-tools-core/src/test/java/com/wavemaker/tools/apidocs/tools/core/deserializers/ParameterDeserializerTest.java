/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
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
