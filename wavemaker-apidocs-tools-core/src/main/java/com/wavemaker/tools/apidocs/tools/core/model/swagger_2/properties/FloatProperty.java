package com.wavemaker.tools.apidocs.tools.core.model.swagger_2.properties;

import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.Xml;

public class FloatProperty extends AbstractNumericProperty implements Property {
    public FloatProperty() {
        super.type = "number";
        super.format = "float";
    }

    public FloatProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public FloatProperty example(Float example) {
        this.setExample(String.valueOf(example));
        return this;
    }

    public static boolean isType(String type, String format) {
        if ("number".equals(type) && "float".equals(format))
            return true;
        else return false;
    }
}