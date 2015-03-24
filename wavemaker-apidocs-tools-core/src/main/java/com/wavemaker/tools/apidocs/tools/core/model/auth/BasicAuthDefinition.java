package com.wavemaker.tools.apidocs.tools.core.model.auth;

import com.wavemaker.tools.apidocs.tools.core.model.AbstractExtensibleEntity;

public class BasicAuthDefinition extends AbstractExtensibleEntity implements SecuritySchemeDefinition {
    private String type = "basic";
    
    public BasicAuthDefinition() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
