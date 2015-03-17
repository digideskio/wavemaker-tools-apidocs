package com.wavemaker.tools.apidocs.tools.core.model.swagger_2.parameters;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.properties.Property;

@JsonPropertyOrder({"name", "in", "description", "required", "type", "items", "collectionFormat", "default"})
public class FormParameter extends AbstractParameter implements SerializableParameter {
    protected String type;
    protected String format;
    protected String collectionFormat;
    protected Property items;

    @JsonProperty("default")
    protected String defaultValue;
    protected List<String> _enum;

    public FormParameter() {
        super.setIn("formData");
    }

    public FormParameter type(String type) {
        this.setType(type);
        return this;
    }

    public FormParameter property(Property property) {
        this.setProperty(property);
        return this;
    }

    public FormParameter description(String description) {
        this.setDescription(description);
        return this;
    }

    public FormParameter name(String name) {
        this.setName(name);
        return this;
    }

    public FormParameter collectionFormat(String collectionFormat) {
        this.setCollectionFormat(collectionFormat);
        return this;
    }

    public FormParameter array(boolean isArray) {
        this.setArray(isArray);
        return this;
    }

    public FormParameter items(Property items) {
        this.items = items;
        return this;
    }

    public FormParameter _enum(List<String> value) {
        this._enum = value;
        return this;
    }

    public List<String> getEnum() {
        return _enum;
    }

    public void setEnum(List<String> _enum) {
        this._enum = _enum;
    }

    public void setArray(boolean isArray) {
        this.type = "array";
    }

    public void setItems(Property items) {
        this.items = items;
    }

    public Property getItems() {
        return items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCollectionFormat() {
        return collectionFormat;
    }

    public void setCollectionFormat(String collectionFormat) {
        this.collectionFormat = collectionFormat;
    }

    public void setProperty(Property property) {
        this.type = property.getType();
        this.format = property.getFormat();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
