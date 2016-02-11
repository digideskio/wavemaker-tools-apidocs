package com.wavemaker.tools.apidocs.tools.core.model.parameters;

public class HeaderParameter extends AbstractSerializableParameter<HeaderParameter>{
    public HeaderParameter() {
        super.setIn("header");
    }
}
