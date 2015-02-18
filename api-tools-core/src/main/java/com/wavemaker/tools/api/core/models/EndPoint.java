/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.models;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Parent;

import com.wavemaker.tools.apidocs.core.model.DocType;
import com.wavemaker.tools.elasticsearch.base.BaseDocument;
import com.wavemaker.tools.elasticsearch.model.DocumentType;


/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
@Document(indexName = "apidoc-registry", type = DocType.APIDOCS_ENDPOINT, indexStoreType = "memory", shards = 1, replicas = 0, refreshInterval = "-1")
public class EndPoint extends BaseDocument {

    @Field(type = FieldType.String)
	@Parent(type = DocType.APIDOCS_API)
	private String parentId;
    
    @Field(type = FieldType.String, index =FieldIndex.not_analyzed)
    private String name;
    
    @Field(type = FieldType.String, index =FieldIndex.not_analyzed)
    private String description;
    
    @Field(type = FieldType.String, index =FieldIndex.not_analyzed)
    private String relativePath;
    
    @Field(type=FieldType.String, index =FieldIndex.not_analyzed)
    private String docLink;

   
    private transient List<Operation> operations;

    public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

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
     * @return the Relative Path that this {@link EndPoint} object can be accessed.
     * Must not be null
     */
    public final String getRelativePath() {
        return relativePath;
    }

    /**
     * Sets the Relative Path.
     */
    public final void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    /**
     * @return the external documentation link.
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
     * @return List of {@link Operation} objects that this Endpoint has.
     * Must not be null
     */
    public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	@Override
	public DocumentType getType() {
		return DocumentType.APIDOCS_ENDPOINT;
	}

    @Override
    public void setDomain(String host) {
        super.setDomain(host);
        if (operations != null) {
            for (Operation operation : operations) {
                operation.setDomain(host);
            }
        }
    }

    @Override
	public String toString() {
		
    	StringBuilder toStrBuilder = new StringBuilder("EndPoint { parentId = '");
		toStrBuilder.append(parentId).append('\'');
		toStrBuilder.append(", name = '").append(name).append('\'');
		toStrBuilder.append(", description = '").append(description).append('\'');
		toStrBuilder.append(", relativePath = '").append(relativePath).append('\'');
		toStrBuilder.append(", docLink = '").append(docLink).append('\'');
		toStrBuilder.append(", operations = '").append(operations).append('\'');
		toStrBuilder.append('}');
		return toStrBuilder.toString();
	}
}

