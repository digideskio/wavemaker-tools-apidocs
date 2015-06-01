package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class ArrayProperty extends AbstractProperty implements Property {
    public static final String IS_LIST_EXT = "IS_LIST";

    protected Boolean uniqueItems;
    protected Property items;

    public ArrayProperty() {
        super.type = "array";
    }

    public ArrayProperty(Property items) {
        super.type = "array";
        setItems(items);
    }

    public ArrayProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public ArrayProperty uniqueItems() {
        this.setUniqueItems(true);
        return this;
    }

    public ArrayProperty description(String description) {
        this.setDescription(description);
        return this;
    }

    public ArrayProperty items(Property items) {
        setItems(items);
        return this;
    }

    public Property getItems() {
        return items;
    }

    public void setItems(Property items) {
        this.items = items;
    }

    public Boolean getUniqueItems() {
        return uniqueItems;
    }

    public void setUniqueItems(Boolean uniqueItems) {
        if (Boolean.TRUE.equals(uniqueItems))
            this.uniqueItems = true;
        else
            this.uniqueItems = null;
    }

    @JsonIgnore
    public void setIsList(boolean list) {
        addWMExtension(IS_LIST_EXT, list);
    }

    @JsonIgnore
    public boolean isList() {
        Object list = getWMExtension(IS_LIST_EXT);
        return (list != null) && (boolean) list;
    }
}