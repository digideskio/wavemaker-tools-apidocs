package com.wavemaker.tools.apidocs.tools.core.model.swagger_2.properties;

import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.Xml;

public class FileProperty extends AbstractProperty implements Property {
    public FileProperty() {
        super.type = "file";
    }

    public FileProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public static boolean isType(String type, String format) {
        if (type != null && "file".equals(type.toLowerCase()))
            return true;
        else return false;
    }
}