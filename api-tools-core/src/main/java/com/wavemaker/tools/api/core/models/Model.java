/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.models;

import java.util.List;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
public class Model {
	
    private long modelId;
	private String id;
    private String name;
    private List<Property> properties;
    private String description;
    private String baseModel;
    private String discriminator;
    private List<String> subTypes;


    public Model(
            final String id, final String name, final List<Property> properties, final String description,
            final String baseModel, final String discriminator, final List<String> subTypes) {
        this.id = id;
        this.name = name;
        this.properties = properties;
        this.description = description;
        this.baseModel = baseModel;
        this.discriminator = discriminator;
        this.subTypes = subTypes;
    }

    public Model() {
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
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

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(final List<Property> properties) {
        this.properties = properties;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getBaseModel() {
        return baseModel;
    }

    public void setBaseModel(final String baseModel) {
        this.baseModel = baseModel;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(final String discriminator) {
        this.discriminator = discriminator;
    }

    public List<String> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(final List<String> subTypes) {
        this.subTypes = subTypes;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", properties=" + properties +
                ", description='" + description + '\'' +
                ", baseModel='" + baseModel + '\'' +
                ", discriminator='" + discriminator + '\'' +
                ", subTypes=" + subTypes +
                '}';
    }
}
