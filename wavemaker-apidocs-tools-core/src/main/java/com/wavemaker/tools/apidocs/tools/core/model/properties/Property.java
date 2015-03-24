package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wavemaker.tools.apidocs.tools.core.deserializers.PropertyDeserializer;
import com.wavemaker.tools.apidocs.tools.core.model.Xml;

@JsonDeserialize(using = PropertyDeserializer.class)
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