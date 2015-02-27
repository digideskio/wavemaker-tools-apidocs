/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.wavemaker.tools.apidocs.core.model.DocType;
import com.wavemaker.tools.elasticsearch.base.BaseDocument;
import com.wavemaker.tools.elasticsearch.model.DocumentType;


/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
@Document(indexName = "apidoc-registry", type = DocType.APIDOCS_API, indexStoreType = "memory", shards = 1, replicas = 0, refreshInterval = "-1")
public class ApiDocument extends BaseDocument {

	@Field(type = FieldType.String)
	private String version;

    @Field(type = FieldType.Long)
    private long productId;

    @Field(type = FieldType.Long)
    private long categoryId;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String name; 
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String description;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String baseURL;

    private List<EndPoint> endPoints;

    private List<Model> models;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String relativePath;
     
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String fullyQualifiedName;
    
    /**
     * @return the Name.
     * Must not be null
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the Name.
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * @return the Description.
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Sets the Description.
     */
    public final void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the Base URL that this {@link ApiDocument} can be accessed.
     * May be null
     */
    public final String getBaseURL() {
        return baseURL;
    }

    /**
     * Sets the Base URL that this {@link ApiDocument} can be accessed
     */
    public final void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    /**
     * 
     * @return list of {@link EndPoint} that this api object is associated with
     */
	public List<EndPoint> getEndPoints() {
		return endPoints;
	}

	/**
	 * 
	 * @param endPoints
	 */
	public void setEndPoints(List<EndPoint> endPoints) {
		this.endPoints = endPoints;
	}

	/**
	 * 
	 * @return
	 */
	public List<Model> getModels() {
		return models;
	}

	/**
	 * 
	 * @param models
	 */
	public void setModels(List<Model> models) {
		this.models = models;
	}

	@Override
	public DocumentType getType() {
		return DocumentType.APIDOCS_API;
	}

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

    @Override
    public void setDomain(String host) {
        super.setDomain(host);

        if(endPoints != null) {
            for (EndPoint endPoint : endPoints) {
                endPoint.setDomain(host);
            }
        }
    }
    
    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public void setFullyQualifiedName(final String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }


	@Override
	public String toString() {
		 
		StringBuilder toStrBuidler = new StringBuilder("ApiDocument {");
		toStrBuidler.append("  version = '").append(version).append('\'');
		toStrBuidler.append(", name = '").append(name).append('\'');
		toStrBuidler.append(", fullyQualifiedName = '").append(fullyQualifiedName).append('\'');
		toStrBuidler.append(", description = '").append(description).append('\'');
		toStrBuidler.append(", baseURL = '").append(baseURL).append('\'');
		toStrBuidler.append(", endPoints = '").append(endPoints).append('\'');
		toStrBuidler.append(", models=").append(models).append('\'');
		toStrBuidler.append(", relativePath = '").append(relativePath).append('\'');
		toStrBuidler.append("}");
		
		return toStrBuidler.toString();
	}	
}




