package com.wavemaker.tools.apidocs.tools.core.model.swagger_2.properties;

import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.Xml;

public class ObjectProperty extends AbstractProperty implements Property {
    public ObjectProperty() {
        super.type = "object";
    }

    public ObjectProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public ObjectProperty example(String example) {
        this.setExample(example);
        return this;
    }

    //TODO: implement additional formats
    public static boolean isType(String type, String format) {
        if ("object".equals(type))
            return true;
        else return false;
    }
}