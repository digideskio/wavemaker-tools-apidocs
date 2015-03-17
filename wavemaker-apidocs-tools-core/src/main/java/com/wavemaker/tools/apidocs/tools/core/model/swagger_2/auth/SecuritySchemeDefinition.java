package com.wavemaker.tools.apidocs.tools.core.model.swagger_2.auth;

import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.ExtensibleEntity;

public interface SecuritySchemeDefinition extends ExtensibleEntity {
    String getType();

    void setType(String type);
}