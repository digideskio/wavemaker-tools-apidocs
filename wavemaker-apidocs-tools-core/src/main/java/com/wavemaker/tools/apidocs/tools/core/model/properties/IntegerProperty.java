package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class IntegerProperty extends AbstractNumericProperty implements Property {
    public static final String TYPE = "integer";

    public IntegerProperty() {
        super.type = TYPE;
        super.format = "int32";
    }

    public IntegerProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public IntegerProperty example(Integer example) {
        this.setExample(String.valueOf(example));
        return this;
    }

    public static boolean isType(String type, String format) {
        if ("integer".equals(type) && "int32".equals(format))
            return true;
        else return false;
    }
}