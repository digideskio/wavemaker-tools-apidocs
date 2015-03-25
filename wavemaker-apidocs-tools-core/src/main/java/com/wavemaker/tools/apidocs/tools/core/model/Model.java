package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.wavemaker.tools.apidocs.tools.core.deserializers.resolvers.CustomModelTypeIdResolver;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.core.model.serializers.ModelDeserializer;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="type", defaultImpl = RefModel.class)
//@JsonSubTypes({	@Type(value = ArrayModel.class, name = "array"),
//				@Type(value = ComposedModel.class, name = "allOf"),
//				@Type(value = ModelImpl.class, name = "object"),
//				@Type(value = RefModel.class, name = "ref")
//			})
//@JsonTypeIdResolver(CustomModelTypeIdResolver.class)
//@JsonDeserialize(using = ModelDeserializer.class)
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
