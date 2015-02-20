/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.builder;

import java.util.List;

import com.wavemaker.tools.apidocs.tools.core.model.Property;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
public class PropertyBuilder implements EntityBuilder<Property> {

    private String id;
    private String name;
    private String type;
    private List<String> typeArguments;
    private String fullyQualifiedType;
    private String format;
    private boolean isRequired;
    private String description;
    private List<String> allowableValues;
    private double minimum;
    private double maximum;

    public void setId(final String id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setType(final PrimitiveType primitiveType) {
        this.type = primitiveType.getType();
        this.format = primitiveType.getFormat();
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setTypeArguments(final List<String> typeArguments) {
        this.typeArguments = typeArguments;
    }

    public void setFullyQualifiedType(final Class<?> dataType) {
        this.setFullyQualifiedType(dataType.getName());
    }

    public void setFullyQualifiedType(final String fullyQualifiedType) {
        this.fullyQualifiedType = fullyQualifiedType;
    }

    public void setFormat(final String format) {
        this.format = format;
    }

    public void setRequired(final boolean isRequired) {
        this.isRequired = isRequired;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setAllowableValues(final List<String> allowableValues) {
        this.allowableValues = allowableValues;
    }

    public void setMinimum(final double minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(final double maximum) {
        this.maximum = maximum;
    }

    @Override
    public Property build() {
        return new Property(id, name, type, typeArguments, fullyQualifiedType, format, isRequired, description,
                allowableValues, minimum, maximum);
    }
}
