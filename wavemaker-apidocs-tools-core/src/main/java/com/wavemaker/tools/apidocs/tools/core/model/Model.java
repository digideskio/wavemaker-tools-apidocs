package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="x-modelType")
@JsonSubTypes({	@Type(value = ArrayModel.class, name = "ArrayModel"),
				@Type(value = ComposedModel.class, name = "ComposedModel"),
				@Type(value = ModelImpl.class, name = "ModelImpl"),
				@Type(value = RefModel.class, name = "RefModel")
			})
public interface Model {
    String getDescription();

    void setDescription(String description);

    Map<String, Property> getProperties();

    void setProperties(Map<String, Property> properties);

    String getExample();

    void setExample(String example);

    ExternalDocs getExternalDocs();

    Object clone();
}
