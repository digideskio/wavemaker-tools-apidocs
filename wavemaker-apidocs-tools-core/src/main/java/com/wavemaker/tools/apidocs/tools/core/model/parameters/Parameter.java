package com.wavemaker.tools.apidocs.tools.core.model.parameters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wavemaker.tools.apidocs.tools.core.model.ExtensibleEntity;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="in", defaultImpl = RefParameter.class)
@JsonSubTypes({	@Type(value = BodyParameter.class, name = "body"),
				@Type(value = CookieParameter.class, name = "cookie"),
				@Type(value = PathParameter.class, name = "path"),
				@Type(value = FormParameter.class, name = "formData"),
				@Type(value = HeaderParameter.class, name = "header"),
				@Type(value = QueryParameter.class, name = "query"),
				@Type(value = RefParameter.class, name = "ref")
			})
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