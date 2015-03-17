/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.adapter;

import com.wavemaker.tools.apidocs.tools.parser.impl.ReflectionModelParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.ModelParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public class ReflectionTypeParser implements TypeParser {
    @Override
    public TypeParserResponse parseType(final Class<?> type) {
        ModelParser parser = new ReflectionModelParser(type);
        return new TypeParserResponse(TypeParserResponse.ACCEPTED, parser.parse());
    }
}
