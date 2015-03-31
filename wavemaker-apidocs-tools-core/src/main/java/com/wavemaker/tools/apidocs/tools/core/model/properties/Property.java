package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.wavemaker.tools.apidocs.tools.core.deserializers.PropertyDeserializer;
import com.wavemaker.tools.apidocs.tools.core.deserializers.resolvers.CustomPropertyTypeIdResolver;
import com.wavemaker.tools.apidocs.tools.core.model.Xml;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="type", defaultImpl = RefProperty.class)
//@JsonSubTypes({ @Type(value = ArrayProperty.class, name = "array"),
//                @Type(value = BooleanProperty.class, name = "boolean"),
//                @Type(value = DateProperty.class, name = "date"),
//                @Type(value = DateTimeProperty.class, name = "date-time"),
//                @Type(value = DecimalProperty.class, name = "number"),
//                @Type(value = DoubleProperty.class, name = "double"),
//                @Type(value = FloatProperty.class, name = "float"),
//                @Type(value = IntegerProperty.class, name = "integer"),
//                @Type(value = LongProperty.class, name = "integer"),
//                @Type(value = FileProperty.class, name = "file"),
//                @Type(value = MapProperty.class, name = "object"),
//                @Type(value = ObjectProperty.class, name = "object"),
//                @Type(value = RefProperty.class, name = "ref"),
//                @Type(value = StringProperty.class, name = "string"),
//                @Type(value = UUIDProperty.class, name = "uuid"),
//})
//@JsonTypeIdResolver(CustomPropertyTypeIdResolver.class)
//@JsonDeserialize(using = PropertyDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Property {
    Property title(String title);

    Property description(String description);

    String getType();

    String getFormat();

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String title);

    @JsonIgnore
    String getName();

    void setName(String name);

    @JsonIgnore
    boolean getRequired();

    void setRequired(boolean required);

    String getDefault();

    void setDefault(String _default);

    String getExample();

    void setExample(String example);

    Boolean getReadOnly();

    void setReadOnly(Boolean example);

    void setPosition(Integer position);

    Integer getPosition();

    Xml getXml();

    void setXml(Xml xml);
}