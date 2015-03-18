package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.Map;

import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

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
