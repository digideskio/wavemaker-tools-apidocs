/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.builder;

import com.wavemaker.tools.apidocs.tools.core.model.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.ParameterType;
import com.wavemaker.tools.apidocs.tools.core.model.Property;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
public class ParameterBuilder extends PropertyBuilder {

    private int index;
    private ParameterType parameterType;
    private String defaultValue;
    private String docLink;
    private boolean isEditable;
    private String resolver;

    public void setIndex(final int index) {
        this.index = index;
    }

    public void setParameterType(final ParameterType parameterType) {
        this.parameterType = parameterType;
    }

    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDocLink(final String docLink) {
        this.docLink = docLink;
    }

    public void setEditable(final boolean isEditable) {
        this.isEditable = isEditable;
    }

    public void setResolver(final String resolver) {
        this.resolver = resolver;
    }

    @Override
    public Parameter build() {
        Property property = super.build();
        return new Parameter(index, parameterType, defaultValue, docLink, isEditable, resolver, property);
    }
}
