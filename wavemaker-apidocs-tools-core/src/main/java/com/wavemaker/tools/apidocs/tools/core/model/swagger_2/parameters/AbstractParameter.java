package com.wavemaker.tools.apidocs.tools.core.model.swagger_2.parameters;

import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.AbstractExtensibleEntity;

public abstract class AbstractParameter extends AbstractExtensibleEntity implements Parameter {
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

}
