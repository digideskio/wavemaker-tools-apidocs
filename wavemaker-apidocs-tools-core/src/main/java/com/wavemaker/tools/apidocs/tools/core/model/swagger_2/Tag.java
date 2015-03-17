package com.wavemaker.tools.apidocs.tools.core.model.swagger_2;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tag extends AbstractExtensibleEntity {
    public static final String FULLY_QUALIFIED_NAME_EXT = "FULLY_QUALIFIED_NAME";
    public static final String VERSION_EXT = "VERSION";

    private String name;
    private String description;
    private ExternalDocs externalDocs;

    public Tag name(String name) {
        setName(name);
        return this;
    }

    public Tag description(String description) {
        setDescription(description);
        return this;
    }

    public Tag externalDocs(ExternalDocs externalDocs) {
        setExternalDocs(externalDocs);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExternalDocs getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(ExternalDocs externalDocs) {
        this.externalDocs = externalDocs;
    }

    @JsonIgnore
    public void setFullyQualifiedName(String fullyQualifiedName) {
        addExtension(FULLY_QUALIFIED_NAME_EXT, fullyQualifiedName);
    }

    @JsonIgnore
    public void setVersion(String version) {
        addExtension(VERSION_EXT, version);
    }


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Tag {\n");
        b.append("\tname: ").append(getName()).append("\n");
        b.append("\tdescription: ").append(getDescription()).append("\n");
        b.append("\texternalDocs: ").append(getExternalDocs()).append("\n");
        b.append("}");
        return b.toString();
    }
}