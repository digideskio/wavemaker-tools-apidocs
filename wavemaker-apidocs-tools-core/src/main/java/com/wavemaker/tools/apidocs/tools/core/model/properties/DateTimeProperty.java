package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class DateTimeProperty extends AbstractProperty implements Property {
    public DateTimeProperty() {
        super.type = "string";
        super.format = "date-time";
    }

    public DateTimeProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public DateTimeProperty example(String example) {
        this.setExample(example);
        return this;
    }

    public static boolean isType(String type, String format) {
        if ("string".equals(type) && "date-time".equals(format))
            return true;
        else return false;
    }
}