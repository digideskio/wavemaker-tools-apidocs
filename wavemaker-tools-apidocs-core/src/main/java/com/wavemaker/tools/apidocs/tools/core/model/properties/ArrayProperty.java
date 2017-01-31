/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tools.apidocs.tools.core.model.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wavemaker.tools.apidocs.tools.core.model.VendorUtils;
import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class ArrayProperty extends AbstractProperty implements Property {
    public static final String IS_LIST_EXT = "IS_LIST";
    public static final String TYPE = "array";

    protected Boolean uniqueItems = false;
    protected Property items;

    public ArrayProperty() {
        super.type = TYPE;
    }

    public ArrayProperty(Property items) {
        super.type = TYPE;
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
        VendorUtils.addWMExtension(this, IS_LIST_EXT, list);
    }

    @JsonIgnore
    public boolean isList() {
        Object list = VendorUtils.getWMExtension(this, IS_LIST_EXT);
        return (list != null) && (boolean) list;
    }
}