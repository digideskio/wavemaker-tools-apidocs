package com.wavemaker.tools.apidocs.tools.core.model.parameters;

import java.util.List;

import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

public interface SerializableParameter extends Parameter {
    String getType();

    void setType(String type);

    Property getItems();

    void setItems(Property items);

    String getFormat();

    void setFormat(String format);

    String getCollectionFormat();

    void setCollectionFormat(String collectionFormat);

    List<String> getEnum();

    void setEnum(List<String> _enum);

    Integer getMaxLength();

    void setMaxLength(Integer maxLength);

    Integer getMinLength();

    void setMinLength(Integer minLength);

    String getPattern();

    void setPattern(String pattern);

    Boolean isUniqueItems();

    void setUniqueItems(Boolean uniqueItems);

    Number getMultipleOf();

    void setMultipleOf(Number multipleOf);

    Boolean isExclusiveMaximum();

    void setExclusiveMaximum(Boolean exclusiveMinimum);

    Boolean isExclusiveMinimum();

    void setExclusiveMinimum(Boolean exclusiveMinimum);

    Double getMaximum();

    void setMaximum(Double maximum);

    Double getMinimum();

    void setMinimum(Double minimum);

    Integer getMaxItems();

    void setMaxItems(Integer maxItems);

    Integer getMinItems();

    void setMinItems(Integer minItems);
}