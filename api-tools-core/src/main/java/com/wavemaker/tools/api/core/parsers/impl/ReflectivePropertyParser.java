/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.parsers.impl;

import java.lang.reflect.Type;

import com.wavemaker.tools.api.core.builders.PropertyBuilder;
import com.wavemaker.tools.api.core.models.Property;
import com.wavemaker.tools.api.core.parsers.PropertyParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 24/11/14
 */
public class ReflectivePropertyParser extends AbstractPropertyTypeParser<Property, PropertyBuilder> implements
        PropertyParser {
    protected final String propertyName;

    protected ReflectivePropertyParser(final Type type, final String propertyName) {
        super(type);
        this.propertyName = propertyName;
    }

    @Override
    protected Property postProcessBuilder(final PropertyBuilder builder) {
        builder.setId(propertyName);
        builder.setName(propertyName);
        return builder.build();
    }

    @Override
    protected PropertyBuilder newBuilder() {
        return new PropertyBuilder();
    }
}
