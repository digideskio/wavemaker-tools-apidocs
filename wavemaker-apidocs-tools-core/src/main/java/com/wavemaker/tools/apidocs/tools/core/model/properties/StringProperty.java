package com.wavemaker.tools.apidocs.tools.core.model.properties;

import java.util.ArrayList;
import java.util.List;

import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class StringProperty extends AbstractProperty implements Property {
    public static final String TYPE = "string";

    protected List<String> _enum;
    protected Integer minLength = null, maxLength = null;
    protected String pattern = null;

    public enum Format {
        BYTE("byte"),
        URI("uri"),
        URL("url");

        private final String name;

        Format(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Format fromName(String name) {
            for (Format item : values()) {
                if (item.getName().equals(name)) {
                    return item;
                }
            }
            return null;
        }
    }

    public StringProperty() {
        this((String) null);
    }

    public StringProperty(Format format) {
        this(format.getName());
    }

    public StringProperty(String format) {
        super.type = TYPE;
        super.format = format;
    }

    public StringProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public StringProperty example(String example) {
        this.setExample(example);
        return this;
    }

    public StringProperty minLength(Integer minLength) {
        this.setMinLength(minLength);
        return this;
    }

    public StringProperty maxLength(Integer maxLength) {
        this.setMaxLength(maxLength);
        return this;
    }

    public StringProperty pattern(String pattern) {
        this.setPattern(pattern);
        return this;
    }

    public StringProperty _enum(String value) {
        if (this._enum == null)
            this._enum = new ArrayList<String>();
        if (!_enum.contains(value))
            _enum.add(value);
        return this;
    }

    public StringProperty _enum(List<String> value) {
        this._enum = value;
        return this;
    }

    public List<String> getEnum() {
        return _enum;
    }

    public void setEnum(List<String> _enum) {
        this._enum = _enum;
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

    //TODO: implement additional formats
    public static boolean isType(String type, String format) {
        if ("string".equals(type) && (format == null || "uri".equals(format) || "byte".equals(format)))
            return true;
        else return false;
    }
}