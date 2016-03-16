package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class DoubleProperty extends AbstractNumericProperty implements Property {
    public DoubleProperty() {
        super.type = "number";
        super.format = "double";
    }

    public DoubleProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public DoubleProperty example(Double example) {
        this.setExample(String.valueOf(example));
        return this;
    }

    public static boolean isType(String type, String format) {
        if ("number".equals(type) && "double".equals(format))
            return true;
        else return false;
    }
}