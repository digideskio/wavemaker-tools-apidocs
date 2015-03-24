package com.wavemaker.tools.apidocs.tools.core.model.auth;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wavemaker.tools.apidocs.tools.core.model.ExtensibleEntity;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="authType")
@JsonSubTypes({	@Type(value = ApiKeyAuthDefinition.class, name = "ApiKeyAuthDefinition"),
				@Type(value = BasicAuthDefinition.class, name = "BasicAuthDefinition"),
				@Type(value = OAuth2Definition.class, name = "OAuth2Definition")
			})
public interface SecuritySchemeDefinition extends ExtensibleEntity {
    String getType();

    void setType(String type);
}