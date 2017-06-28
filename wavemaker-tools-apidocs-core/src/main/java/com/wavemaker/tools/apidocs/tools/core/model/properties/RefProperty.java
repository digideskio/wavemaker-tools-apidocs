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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.wavemaker.tools.apidocs.tools.core.model.VendorUtils;
import com.wavemaker.tools.apidocs.tools.core.utils.JsonUtils;

public class RefProperty extends AbstractProperty {
    public static final String TYPE_ARGUMENTS_EXT = "TYPE_ARGUMENTS";

    private static final TypeReference<List<Property>> propertyListTypeRef = new TypeReference<List<Property>>() {
        public Type getType() {
            return new ParameterizedType() {
                @Override
                public Type[] getActualTypeArguments() {
                    return new Type[]{Property.class};
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

    String ref;

    public RefProperty() {
        super.type = "ref";
    }

    public RefProperty(String ref) {
        super.type = "ref";
        set$ref(ref);
    }

    public RefProperty asDefault(String ref) {
        this.set$ref("#/definitions/" + ref);
        return this;
    }

    public RefProperty description(String description) {
        this.setDescription(description);
        return this;
    }

    @Override
    @JsonIgnore
    public String getType() {
        return this.type;
    }

    @Override
    @JsonIgnore
    public void setType(String type) {
        this.type = type;
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
    public String getSimpleRef() {
        if (ref.indexOf("#/definitions/") == 0)
            return ref.substring("#/definitions/".length());
        else
            return ref;
    }

    public static boolean isType(String type, String format) {
        if ("$ref".equals(type))
            return true;
        else return false;
    }

    @JsonIgnore
    public void setTypeArguments(List<Property> properties) {
        VendorUtils.addWMExtension(this, TYPE_ARGUMENTS_EXT, properties);
    }

    @JsonIgnore
    public List<Property> getTypeArguments() {
        Object typeArguments = VendorUtils.getWMExtension(this, TYPE_ARGUMENTS_EXT);
        if (typeArguments == null) {
            return Collections.<Property>emptyList();
        } else {
            return JsonUtils.getInstance().convertValue(typeArguments, propertyListTypeRef);
        }
    }
}