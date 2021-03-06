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
package com.wavemaker.tools.apidocs.tools.core.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.core.model.properties.RefProperty;

public class RefModel implements Model, ExtensibleEntity {

    private static final ObjectMapper objectMapper = new ObjectMapper(); // configuration shared across objects.
    private static final TypeReference<List<Property>> propertyListTypeRef = new TypeReference<List<Property>>() {
        public Type getType() {
            return new ParameterizedType() {
                @Override
                public Type[] getActualTypeArguments() {
                    return new Type[]{Model.class};
                }

                @Override
                public Type getRawType() {
                    return List.class;
                }

                @Override
                public Type getOwnerType() {
                    return null;
                }
            };
        }
    };
    private Map<String, Object> vendorExtensions = new HashMap<>();
    // internally, the ref value is never fully qualified
    private String ref;
    private String description;
    private ExternalDocs externalDocs;
    private Map<String, Property> properties;
    private String example;

    public RefModel() {
    }

    public RefModel(String ref) {
        set$ref(ref);
    }

    public RefModel asDefault(String ref) {
        this.set$ref("#/definitions/" + ref);
        return this;
    }

    // not allowed in a $ref
    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Map<String, Property> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Property> properties) {
        this.properties = properties;
    }

    @JsonIgnore
    public String getSimpleRef() {
        if (ref.indexOf("#/definitions/") == 0)
            return ref.substring("#/definitions/".length());
        else
            return ref;
    }

    public String get$ref() {
        if (ref.startsWith("http"))
            return ref;
        else
            return "#/definitions/" + ref;
    }

    public void set$ref(String ref) {
        if (ref.indexOf("#/definitions/") == 0)
            this.ref = ref.substring("#/definitions/".length());
        else
            this.ref = ref;
    }

    @JsonIgnore
    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @JsonIgnore
    public ExternalDocs getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(ExternalDocs value) {
        externalDocs = value;
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
    public void setTypeArguments(List<Model> properties) {
        VendorUtils.addWMExtension(this, RefProperty.TYPE_ARGUMENTS_EXT, properties);
    }

    @JsonIgnore
    public List<Model> getTypeArguments() {
        Object typeArguments = VendorUtils.getWMExtension(this, RefProperty.TYPE_ARGUMENTS_EXT);
        if (typeArguments == null) {
            return Collections.<Model>emptyList();
        } else {
            return objectMapper.convertValue(typeArguments, propertyListTypeRef);
        }
    }

    public Object clone() {
        RefModel cloned = new RefModel();
        cloned.ref = this.ref;
        cloned.description = this.description;
        cloned.properties = this.properties;
        cloned.example = this.example;
        cloned.vendorExtensions = this.vendorExtensions;

        return cloned;
    }
}