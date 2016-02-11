package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class DecimalProperty extends AbstractNumericProperty implements Property {
    public static final String TYPE = "number";

    public DecimalProperty() {
        super.type =  TYPE;
    }

    public DecimalProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public DecimalProperty example(String example) {
        this.setExample(example);
        return this;
    }

    public static boolean isType(String type, String format) {
        if ("number".equals(type) && format == null)
            return true;
        else return false;
    }
}