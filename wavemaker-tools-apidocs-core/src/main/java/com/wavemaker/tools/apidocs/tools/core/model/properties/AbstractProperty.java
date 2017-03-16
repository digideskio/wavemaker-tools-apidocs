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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wavemaker.tools.apidocs.tools.core.model.Constraint;
import com.wavemaker.tools.apidocs.tools.core.model.ExtensibleEntity;
import com.wavemaker.tools.apidocs.tools.core.model.VendorUtils;
import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public abstract class AbstractProperty implements Property, ExtensibleEntity {

    private static final String SUB_FORMAT_EXT = "SUB_FORMAT";
    public static final String CONSTRAINTS_EXT = "CONSTRAINTS";

    private Map<String, Object> vendorExtensions = new HashMap<>();

    String name;
    String type;
    String format;
    String example;
    String _default;
    Xml xml;
    boolean required;
    Integer position;
    String description;
    String title;
    Boolean readOnly;

    public Property title(String title) {
        this.setTitle(title);
        return this;
    }

    public Property description(String description) {
        this.setDescription(description);
        return this;
    }

    public Property readOnly() {
        this.setReadOnly(Boolean.TRUE);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDefault(final String _default) {
        this._default = _default;
    }

    @Override
    public String getDefault() {
        return _default;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Xml getXml() {
        return xml;
    }

    public void setXml(Xml xml) {
        this.xml = xml;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        if (Boolean.FALSE.equals(readOnly))
            this.readOnly = null;
        else
            this.readOnly = readOnly;
    }

    public void setPropertyIdentifier(int id) {

    }

    public void setSubFormat(String subFormat) {
        VendorUtils.addWMExtension(this, SUB_FORMAT_EXT, subFormat);
    }

    @JsonIgnore
    public String getSubFormat() {
        return (String) VendorUtils.getWMExtension(this, SUB_FORMAT_EXT);
    }

    @JsonIgnore
    public List<Constraint> getConstraints() {
        return (List<Constraint>) VendorUtils.getWMExtension(this, CONSTRAINTS_EXT);
    }

    @JsonIgnore
    public void setConstraints(List<Constraint> constraints) {
        VendorUtils.addWMExtension(this, CONSTRAINTS_EXT, constraints);
    }

    @JsonAnyGetter
    public Map<String, Object> getVendorExtensions() {
        return vendorExtensions;
    }

    @JsonAnySetter
    public void setVendorExtension(String name, Object value) {
        if (name.startsWith("x-")) {
            vendorExtensions.put(name, value);
        }
    }
}