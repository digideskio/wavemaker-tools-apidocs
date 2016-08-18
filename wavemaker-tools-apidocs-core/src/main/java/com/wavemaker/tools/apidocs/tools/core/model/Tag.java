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
package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tag implements ExtensibleEntity, Comparable<Tag> {
    public static final String FULLY_QUALIFIED_NAME_EXT = "FULLY_QUALIFIED_NAME";
    public static final String CONTROLLER_NAME_EXT = "CONTROLLER_NAME";
    public static final String VERSION_EXT = "VERSION";

    private Map<String, Object> vendorExtensions = new HashMap<>();

    private String name;
    private String description;
    private ExternalDocs externalDocs;

    public Tag name(String name) {
        setName(name);
        return this;
    }

    public Tag description(String description) {
        setDescription(description);
        return this;
    }

    public Tag externalDocs(ExternalDocs externalDocs) {
        setExternalDocs(externalDocs);
        return this;
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

    public ExternalDocs getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(ExternalDocs externalDocs) {
        this.externalDocs = externalDocs;
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
    public void setFullyQualifiedName(String fullyQualifiedName) {
        VendorUtils.addWMExtension(this, FULLY_QUALIFIED_NAME_EXT, fullyQualifiedName);
    }

    public String getFullyQualifiedName() {
        return (String) VendorUtils.getWMExtension(this, FULLY_QUALIFIED_NAME_EXT);
    }

    @JsonIgnore
    public void setControllerName(String controllerName) {
        VendorUtils.addWMExtension(this, CONTROLLER_NAME_EXT, controllerName);
    }

    public String getControllerName() {
        return (String) VendorUtils.getWMExtension(this, CONTROLLER_NAME_EXT);
    }

    @JsonIgnore
    public void setVersion(String version) {
        VendorUtils.addWMExtension(this, VERSION_EXT, version);
    }

    public String getVersion() {
        return (String) VendorUtils.getWMExtension(this, VERSION_EXT);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Tag {\n");
        b.append("\tname: ").append(getName()).append("\n");
        b.append("\tdescription: ").append(getDescription()).append("\n");
        b.append("\texternalDocs: ").append(getExternalDocs()).append("\n");
        b.append("}");
        return b.toString();
    }

    @Override
    public int compareTo(final Tag other) {
        return getName().compareToIgnoreCase(other.getName());
    }
}