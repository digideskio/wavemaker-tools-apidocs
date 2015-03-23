/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.impl;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 24/11/14
 */
public class SubstituteReflectionModelParser extends ReflectionModelParser {

    private final Class<?> actualModelType;

    public SubstituteReflectionModelParser(final Class<?> substituteModelType, final Class<?> actualModelType) {
        super(substituteModelType);
        this.actualModelType = actualModelType;
    }
}
