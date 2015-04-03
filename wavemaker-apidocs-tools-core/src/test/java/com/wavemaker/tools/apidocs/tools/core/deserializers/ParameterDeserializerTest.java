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

import org.junit.Assert;
import org.junit.Test;

import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;

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
		File outputFile = new File("test_parameters.json");
		Assert.assertFalse(outputFile.exists());
		objectMapper.writeValue(outputFile, parameter);
		Assert.assertTrue(outputFile.exists());
		Assert.assertTrue(outputFile.length() > 0);
		outputFile.delete();
	}
}
