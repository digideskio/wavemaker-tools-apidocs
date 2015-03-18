package com.wavemaker.tools.apidocs.tools.core.model.parameters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wavemaker.tools.apidocs.tools.core.model.AbstractExtensibleEntity;

public abstract class AbstractParameter extends AbstractExtensibleEntity implements Parameter {
    public static final String EDITABLE_EXT = "EDITABLE";

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
        addExtension(EDITABLE_EXT, editable);
    }


}
