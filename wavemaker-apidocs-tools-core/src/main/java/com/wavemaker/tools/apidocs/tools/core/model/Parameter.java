/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
public class Parameter extends Property {
	
	@Field(type = FieldType.Boolean)
    private boolean isArray;
	
	@Field(type = FieldType.Integer)
    private int index;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String docLink;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String defaultValue;

	@Field(type = FieldType.Object)
    private ParameterType parameterType;
	
	
    // Move to View Object
    private boolean isEditable;
    private String resolver;
    
   
    public Parameter(
            final int index, final ParameterType parameterType, final String defaultValue,
            final String docLink, final boolean isEditable, final String resolver, Property property) {
        super(property);
        this.index = index;
        this.parameterType = parameterType;
        this.defaultValue = defaultValue;
        this.docLink = docLink;
        this.isEditable = isEditable;
        this.resolver = resolver;
    }
    
    
    public Parameter() {
    	
    }
    
    /**
     * @return if this parameter is of array type
     */
    public final boolean isArray() {
        return isArray;
    }

    /**
     * Sets the Is Array.
     */
	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

    /**
     * @return the index. By default it is 0. 
     * If this parameter object is of {@link ParameterType#PATH} type, then the index of the parameter object
     * is captured here.
     * 
     */
    public final int getIndex() {
        return index;
    }

    /**
     * Sets the Index.
     */
    public final void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the external documentation link for this parameter object.
     */
    public final String getDocLink() {
        return docLink;
    }

    /**
     * Sets the Doc Link.
     */
    public final void setDocLink(String docLink) {
        this.docLink = docLink;
    }

   
    /**
     * @return the {@link ParameterType}.
     * Must not be null
     */
    public final ParameterType getParameterType() {
        return parameterType;
    }

    /**
     * Sets the {@link ParameterType}.
     */
    public final void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }
    
    /**
     * @return default value for the parameter if exists. Otherwise null;
     */
	public String getDefaultValue() {
		return defaultValue;
	}

	
	/**
	 * sets the defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
    public String getResolver() {
        return resolver;
    }

    public void setResolver(final String resolver) {
        this.resolver = resolver;
    }


	@Override
	public String toString() {
		
		StringBuilder toStrBuilder = new StringBuilder("Parameter { name = '");
		toStrBuilder.append(", description = '").append(getDescription()).append('\'');
		toStrBuilder.append(", fullyQualifiedType = '").append(getFullyQualifiedType()).append('\'');
		toStrBuilder.append(", format = '").append(getFormat()).append('\'');
		toStrBuilder.append(", isRequired = '").append(isRequired()).append('\'');
		toStrBuilder.append(", isArray = '").append(isArray).append('\'');
		toStrBuilder.append(", index = '").append(index).append('\'');
		toStrBuilder.append(", docLink = '").append(docLink).append('\'');
		toStrBuilder.append(", defaultValue = '").append(defaultValue).append('\'');
		toStrBuilder.append(", parameterType = '").append(parameterType).append('\'');
		toStrBuilder.append(", allowableValues = '").append(getAllowableValues()).append('\'');
		toStrBuilder.append(", minimum = '").append(getMinimum()).append('\'');
		toStrBuilder.append(", maximum = '").append(getMaximum()).append('\'');
		toStrBuilder.append('}');
		
		return toStrBuilder.toString();
	}
}