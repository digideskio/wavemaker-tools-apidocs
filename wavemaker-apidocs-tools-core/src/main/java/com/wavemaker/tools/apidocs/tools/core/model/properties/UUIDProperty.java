package com.wavemaker.tools.apidocs.tools.core.model.properties;

import java.util.List;

import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class UUIDProperty extends AbstractProperty implements Property {
    protected List<String> _enum;
    protected Integer minLength = null, maxLength = null;
    protected String pattern = null;

    public UUIDProperty() {
        super.type = "string";
        super.format = "uuid";
    }

    public UUIDProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public UUIDProperty minLength(Integer minLength) {
        this.setMinLength(minLength);
        return this;
    }

    public UUIDProperty maxLength(Integer maxLength) {
        this.setMaxLength(maxLength);
        return this;
    }

    public UUIDProperty pattern(String pattern) {
        this.setPattern(pattern);
        return this;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public static boolean isType(String type, String format) {
        if ("string".equals(type) && "uuid".equals(format))
            return true;
        else return false;
    }
}