package com.wavemaker.tools.apidocs.tools.core.model.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.wavemaker.tools.apidocs.tools.core.resolvers.security.SecuritySchemDefinitionTypeResolver;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonTypeResolver(SecuritySchemDefinitionTypeResolver.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface SecuritySchemeDefinition {
	String getType();

    void setType(String type);
}