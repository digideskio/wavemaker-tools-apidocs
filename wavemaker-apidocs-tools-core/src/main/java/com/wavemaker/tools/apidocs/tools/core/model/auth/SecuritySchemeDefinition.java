package com.wavemaker.tools.apidocs.tools.core.model.auth;

import com.wavemaker.tools.apidocs.tools.core.model.ExtensibleEntity;

public interface SecuritySchemeDefinition extends ExtensibleEntity {
    String getType();

    void setType(String type);
}