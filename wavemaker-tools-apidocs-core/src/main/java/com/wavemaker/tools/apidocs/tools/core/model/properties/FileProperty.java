package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.wavemaker.tools.apidocs.tools.core.model.Xml;

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