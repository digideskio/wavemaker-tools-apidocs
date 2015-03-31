package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="type", defaultImpl = RefModel.class)
//@JsonSubTypes({	@Type(value = ArrayModel.class, name = "array"),
//				@Type(value = ComposedModel.class, name = "allOf"),
//				@Type(value = ModelImpl.class, name = "object"),
//				@Type(value = RefModel.class, name = "ref")
//			})
//@JsonTypeIdResolver(CustomModelTypeIdResolver.class)
//@JsonDeserialize(using = ModelDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
