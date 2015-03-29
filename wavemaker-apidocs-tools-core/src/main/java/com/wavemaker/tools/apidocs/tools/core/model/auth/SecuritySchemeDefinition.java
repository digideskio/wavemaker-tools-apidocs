package com.wavemaker.tools.apidocs.tools.core.model.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wavemaker.tools.apidocs.tools.core.model.ExtensibleEntity;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({	@Type(value = ApiKeyAuthDefinition.class, name = "apiKey"),
				@Type(value = BasicAuthDefinition.class, name = "basic"),
				@Type(value = OAuth2Definition.class, name = "oauth2")
			})
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface SecuritySchemeDefinition extends ExtensibleEntity {
    String getType();

    void setType(String type);
}