/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.models;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
public class Property {

    private String id;
    
    @Field(type = FieldType.String)
    private String name;
    
    private String type;
    
    private List<String> typeArguments;
    
    @Field(type = FieldType.String)
    private String fullyQualifiedType;
    
    @Field(type = FieldType.String)
    private String format;
    
    @Field(type = FieldType.Boolean)
    private boolean isRequired;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String description;
    
    @Field(type = FieldType.Object)
    private List<String> allowableValues;
    
    @Field(type = FieldType.Double)
    private double minimum;
    
    @Field(type = FieldType.Double)
    private double maximum;


    public Property(
            final String id, final String name, final String type, final List<String> typeArguments,
            final String fullyQualifiedType,
            final String format, final boolean isRequired, final String description, final List<String> allowableValues,
            final double minimum, final double maximum) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.typeArguments = typeArguments;
        this.fullyQualifiedType = fullyQualifiedType;
        this.format = format;
        this.isRequired = isRequired;
        this.description = description;
        this.allowableValues = allowableValues;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Property(Property property) {
        this(property.getId(), property.getName(), property.getType(), property.getTypeArguments(),
                property.getFullyQualifiedType(),
                property.getFormat(), property.isRequired(), property.getDescription(),
                property.getAllowableValues(), property.getMinimum(), property.getMaximum());
    }

    public Property() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<String> getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(final List<String> typeArguments) {
        this.typeArguments = typeArguments;
    }

    public String getFullyQualifiedType() {
        return fullyQualifiedType;
    }

    public void setFullyQualifiedType(final String fullyQualifiedType) {
        this.fullyQualifiedType = fullyQualifiedType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(final String format) {
        this.format = format;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(final boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<String> getAllowableValues() {
        return allowableValues;
    }

    public void setAllowableValues(final List<String> allowableValues) {
        this.allowableValues = allowableValues;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(final double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(final double maximum) {
        this.maximum = maximum;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", typeArguments=" + typeArguments +
                ", fullyQualifiedType='" + fullyQualifiedType + '\'' +
                ", format='" + format + '\'' +
                ", isRequired=" + isRequired +
                ", description='" + description + '\'' +
                ", allowableValues=" + allowableValues +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                '}';
    }
}
