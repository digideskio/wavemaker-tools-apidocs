package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

public class RefModel implements Model {
    // internally, the ref value is never fully qualified
    private String ref;
    private String description;
    private ExternalDocs externalDocs;
    private Map<String, Property> properties;
    private String example;

    public RefModel() {
    }

    public RefModel(String ref) {
        set$ref(ref);
    }

    public RefModel asDefault(String ref) {
        this.set$ref("#/definitions/" + ref);
        return this;
    }

    // not allowed in a $ref
    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Map<String, Property> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Property> properties) {
        this.properties = properties;
    }

    @JsonIgnore
    public String getSimpleRef() {
        if (ref.indexOf("#/definitions/") == 0)
            return ref.substring("#/definitions/".length());
        else
            return ref;
    }
    
    @JsonIgnore
    public String get$ref() {
        if (ref.startsWith("http"))
            return ref;
        else
            return "#/definitions/" + ref;
    }

    @JsonIgnore
    public void set$ref(String ref) {
        if (ref.indexOf("#/definitions/") == 0)
            this.ref = ref.substring("#/definitions/".length());
        else
            this.ref = ref;
    }

    @JsonIgnore
    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @JsonIgnore
    public ExternalDocs getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(ExternalDocs value) {
        externalDocs = value;
    }

    public Object clone() {
        RefModel cloned = new RefModel();
        cloned.ref = this.ref;
        cloned.description = this.description;
        cloned.properties = this.properties;
        cloned.example = this.example;

        return cloned;
    }
}