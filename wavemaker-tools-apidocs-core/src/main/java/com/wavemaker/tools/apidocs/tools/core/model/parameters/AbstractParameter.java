/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
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
package com.wavemaker.tools.apidocs.tools.core.model.parameters;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wavemaker.tools.apidocs.tools.core.model.VendorUtils;

public abstract class AbstractParameter implements Parameter {
    public static final String EDITABLE_EXT = "EDITABLE";
    public static final String RESOLVER_EXT = "RESOLVER";
    public static final String FULLY_QUALIFIED_TYPE_EXT = "FULLY_QUALIFIED_TYPE";
    public static final String UUID_EXT = "UUID";

    private Map<String, Object> vendorExtensions = new HashMap<>();

    protected String in;
    protected String name;
    protected String description;
    protected boolean required = false;
    protected String access;

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    @JsonIgnore
    public void setEditable(boolean editable) {
        VendorUtils.addWMExtension(this, EDITABLE_EXT, editable);
    }

    public boolean isEditable() {
        return (boolean) VendorUtils.getWMExtension(this, EDITABLE_EXT);
    }

    @JsonIgnore
    public void setResolver(String resolver) {
        VendorUtils.addWMExtension(this, RESOLVER_EXT, resolver);
    }

    public String getResolver() {
        return (String) VendorUtils.getWMExtension(this, RESOLVER_EXT);
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

    @JsonIgnore
    public void setFullyQualifiedType(String fullyQualifiedType) {
        VendorUtils.addWMExtension(this, FULLY_QUALIFIED_TYPE_EXT, fullyQualifiedType);
    }

    public String getFullyQualifiedType() {
        return (String) VendorUtils.getWMExtension(this, FULLY_QUALIFIED_TYPE_EXT);
    }

    @JsonIgnore
    public void setUuid(String uuid) {
        VendorUtils.addWMExtension(this, UUID_EXT, uuid);
    }

    public String getUuid() {
        return (String) VendorUtils.getWMExtension(this, UUID_EXT);
    }

}
