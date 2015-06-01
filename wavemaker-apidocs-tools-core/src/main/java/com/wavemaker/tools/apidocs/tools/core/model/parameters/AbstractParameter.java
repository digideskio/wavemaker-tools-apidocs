package com.wavemaker.tools.apidocs.tools.core.model.parameters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wavemaker.tools.apidocs.tools.core.model.AbstractExtensibleEntity;

public abstract class AbstractParameter extends AbstractExtensibleEntity implements Parameter {
    public static final String EDITABLE_EXT = "EDITABLE";
    public static final String RESOLVER_EXT = "RESOLVER";
    public static final String FULLY_QUALIFIED_TYPE_EXT = "FULLY_QUALIFIED_TYPE";

    protected String in;
    protected String name;
    protected String description;
    protected boolean required = false;
    protected String access;

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
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

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    @JsonIgnore
    public void setEditable(boolean editable) {
        addWMExtension(EDITABLE_EXT, editable);
    }

    public boolean isEditable() {
        return (boolean) getWMExtension(EDITABLE_EXT);
    }

    @JsonIgnore
    public void setResolver(String resolver) {
        addWMExtension(RESOLVER_EXT, resolver);
    }

    public String getResolver() {
        return (String) getWMExtension(RESOLVER_EXT);
    }

    @JsonIgnore
    public void setFullyQualifiedType(String fullyQualifiedType) {
        addWMExtension(FULLY_QUALIFIED_TYPE_EXT, fullyQualifiedType);
    }

    public String getFullyQualifiedType() {
        return (String) (FULLY_QUALIFIED_TYPE_EXT);
    }


}
