package com.wavemaker.tools.apidocs.tools.core.model.parameters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wavemaker.tools.apidocs.tools.core.model.ArrayModel;
import com.wavemaker.tools.apidocs.tools.core.model.ComposedModel;
import com.wavemaker.tools.apidocs.tools.core.model.ExtensibleEntity;
import com.wavemaker.tools.apidocs.tools.core.model.ModelImpl;
import com.wavemaker.tools.apidocs.tools.core.model.RefModel;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="x-paramType")
@JsonSubTypes({	@Type(value = BodyParameter.class, name = "BodyParameter"),
				@Type(value = CookieParameter.class, name = "CookieParameter"),
				@Type(value = PathParameter.class, name = "PathParameter"),
				@Type(value = FormParameter.class, name = "FormParameter"),
				@Type(value = HeaderParameter.class, name = "HeaderParameter"),
				@Type(value = QueryParameter.class, name = "QueryParameter"),
				@Type(value = RefParameter.class, name = "RefParameter")
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