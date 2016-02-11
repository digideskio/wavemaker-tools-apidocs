package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class BooleanProperty extends AbstractProperty implements Property {

    public static final String TYPE = "boolean";

    public BooleanProperty() {
        super.type = TYPE;
    }

    public BooleanProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public BooleanProperty example(Boolean example) {
        this.setExample(String.valueOf(example));
        return this;
    }

    public static boolean isType(String type, String format) {
        if ("boolean".equals(type))
            return true;
        else return false;
    }
}