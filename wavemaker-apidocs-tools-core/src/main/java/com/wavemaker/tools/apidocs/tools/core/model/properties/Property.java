package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.wavemaker.tools.apidocs.tools.core.model.Xml;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.BodyParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.CookieParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.HeaderParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.PathParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.QueryParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.RefParameter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="x-propType")
@JsonSubTypes({	@Type(value = DecimalProperty.class, name = "DecimalProperty"),
				@Type(value = DoubleProperty.class, name = "DoubleProperty"),
				@Type(value = IntegerProperty.class, name = "IntegerProperty"),
				@Type(value = FloatProperty.class, name = "FloatProperty"),
				@Type(value = LongProperty.class, name = "LongProperty"),
				@Type(value = ArrayProperty.class, name = "ArrayProperty"),
				@Type(value = DateProperty.class, name = "DateProperty"),
				@Type(value = DateTimeProperty.class, name = "DateTimeProperty"),
				@Type(value = FileProperty.class, name = "FileProperty"),
				@Type(value = MapProperty.class, name = "MapProperty"),
				@Type(value = ObjectProperty.class, name = "ObjectProperty"),
				@Type(value = RefProperty.class, name = "RefProperty"),
				@Type(value = StringProperty.class, name = "StringProperty"),
				@Type(value = UUIDProperty.class, name = "UUIDProperty"),
			})
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