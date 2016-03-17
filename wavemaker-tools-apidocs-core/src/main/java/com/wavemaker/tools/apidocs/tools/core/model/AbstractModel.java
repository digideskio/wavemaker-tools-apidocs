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

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;

public abstract class AbstractModel implements Model, ExtensibleEntity {
    private static final String TAG_EXT = "TAGS";
    private static final String FULLY_QUALIFIED_NAME_EXT = "FULLY_QUALIFIED_NAME";

    protected Map<String, Object> vendorExtensions = new HashMap<>();
    private ExternalDocs externalDocs;

    @Override
    public ExternalDocs getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(ExternalDocs value) {
        externalDocs = value;
    }

    public void cloneTo(Object clone) {
        AbstractModel cloned = (AbstractModel) clone;
        cloned.externalDocs = this.externalDocs;
    }

    public Object clone() {
        return null;
    }

    public void addTag(String tag) {
        synchronized (this) {
            List<String> tags = (List<String>) VendorUtils.getWMExtension(this, TAG_EXT);
            if (tags == null) {
                tags = new LinkedList<>();
            }
            if(!tags.contains(tag)) {
                tags.add(tag);
                VendorUtils.addWMExtension(this, TAG_EXT, tags);
            }
        }
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
    public Set<String> getTags() {
        Object tags = VendorUtils.getWMExtension(this, TAG_EXT);
        return (tags == null) ? Collections.<String>emptySet() : Sets.newLinkedHashSet((List<String>) tags);
    }

    @JsonIgnore
    public void setFullyQualifiedName(String fullyQualifiedName) {
        VendorUtils.addWMExtension(this, FULLY_QUALIFIED_NAME_EXT, fullyQualifiedName);
    }

    @JsonIgnore
    public String getFullyQualifiedName() {
        return (String) VendorUtils.getWMExtension(this, FULLY_QUALIFIED_NAME_EXT);
    }
}
