/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.List;
import java.util.Set;


/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
public class Operation {

	private String parentId;

    private String name;

    private String description;

    private String notes;

    private String docLink;

    private String completePath;

    private boolean isDeprecated;

	private HTTPMethod httpMethod;

    private Set<String> produces;

    private Set<String> consumes;

	private List<Parameter> parameters;

    private String returnType;

    private boolean isReturnTypeArray = false;

    private List<ResponseMessage> responseMessages;

    private OperationPolicy policy;

	
	// These should be added to OperationView Object
    private List<String> returnTypeArguments;
    private String accessSpecifier;
    private String methodIdentifier;

	
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
     * @return the method name.
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
     * @return the external documentation link for this method.
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
     * @return if this method is deprecated.
     */
    public final boolean isDeprecated() {
        return isDeprecated;
    }

    /**
     * Sets the Is Deprecated.
     */
    public void setDeprecated(boolean isDeprecated) {
		this.isDeprecated = isDeprecated;
	}

    /**
     * @return the {@link HTTPMethod} that this method supports.
     * Must not be null
     */
    public final HTTPMethod getHttpMethod() {
        return httpMethod;
    }

    /**
     * Sets the HttpMethod.
     */
    public final void setHttpMethod(HTTPMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    /**
     * @return the list of {@link String} those this {@link Operation} object produces.
     * It Must not be null
     */
	public Set<String> getProduces() {
		return produces;
	}

	public void setProduces(Set<String> produces) {
		this.produces = produces;
	}

	/**
     * @return the list of {@link String} those this {@link Operation} object consumes.
     * It Must not be null
     */
	public Set<String> getConsumes() {
		return consumes;
	}

	public void setConsumes(Set<String> consumes) {
		this.consumes = consumes;
	}

	/**
     * @return the list of {@link Parameter} asssociated with this {@link Operation} object.
     * It Must not be null
     */
	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	/**
     * @return the type of the method response.
     * It Must not be null
     */
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
     * @return the list of {@link ResponseMessage} those this {@link Operation} object associated with.
     * May be null
     */
	public List<ResponseMessage> getResponseMessages() {
		return responseMessages;
	}

	public void setResponseMessages(List<ResponseMessage> responseMessages) {
		this.responseMessages = responseMessages;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return 'true' if the return type of this method object is array.
	 * Default value is 'false'.
	 *
	 */
	public boolean isReturnTypeArray() {
		return isReturnTypeArray;
	}

	public void setReturnTypeArray(boolean isReturnTypeArray) {
		this.isReturnTypeArray = isReturnTypeArray;
	}

	public OperationPolicy getPolicy() {
		return policy;
	}

	public void setPolicy(OperationPolicy policy) {
		this.policy = policy;
	}

	public String getCompletePath() {
		return completePath;
	}

	public void setCompletePath(String completePath) {
		this.completePath = completePath;
	}
		
	public List<String> getReturnTypeArguments() {
        return returnTypeArguments;
	}

    public void setReturnTypeArguments(final List<String> returnTypeArguments) {
        this.returnTypeArguments = returnTypeArguments;
    }

    public String getAccessSpecifier() {
        return accessSpecifier;
    }

    public void setAccessSpecifier(final AccessSpecifier specifier) {
        this.setAccessSpecifier(specifier.name());
    }

    public void setAccessSpecifier(final String accessSpecifier) {
        this.accessSpecifier = accessSpecifier;
    }
    
    public String getMethodIdentifier() {
        return methodIdentifier;
    }

    public void setMethodIdentifier(final String methodIdentifier) {
        this.methodIdentifier = methodIdentifier;
    }


	@Override
	public String toString() {
		  
		StringBuilder toStrBuilder = new StringBuilder("Operation { name = '");
		toStrBuilder.append(name).append('\'');
		toStrBuilder.append(", description = '").append(description).append('\'');
		toStrBuilder.append(", notes = '").append(notes).append('\'');
		toStrBuilder.append(", docLink = '").append(docLink).append('\'');
		toStrBuilder.append(", isDeprecated = '").append(isDeprecated).append('\'');
		toStrBuilder.append(", httpMethod = '").append(httpMethod).append('\'');
		toStrBuilder.append(", produces = '").append(produces).append('\'');
		toStrBuilder.append(", consumes = '").append(consumes).append('\'');
		toStrBuilder.append(", parameters = '").append(parameters).append('\'');
		toStrBuilder.append(", returnType = '").append(returnType).append('\'');
		toStrBuilder.append(", returnTypeArguments = '").append(returnTypeArguments).append('\'');
		toStrBuilder.append(", accessSpecifier = '").append(accessSpecifier).append('\'');
		toStrBuilder.append(", responseMessages = '").append(responseMessages).append('\'');
		toStrBuilder.append(", policy = '").append(policy).append('\'');
		toStrBuilder.append(", methodIdentifier = '").append(methodIdentifier).append('\'');
		
		return toStrBuilder.toString();
	}
}

