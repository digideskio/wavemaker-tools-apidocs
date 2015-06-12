package com.wavemaker.tools.apidocs.tools.core.model.parameters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.wavemaker.tools.apidocs.tools.core.model.ExtensibleEntity;
import com.wavemaker.tools.apidocs.tools.core.resolvers.parameter.ParameterTypeResolver;


@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonTypeResolver(ParameterTypeResolver.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface Parameter extends ExtensibleEntity {
    String getIn();

    void setIn(String in);

    @JsonIgnore
    String getAccess();

    @JsonIgnore
    void setAccess(String access);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    boolean getRequired();

    void setRequired(boolean required);
}