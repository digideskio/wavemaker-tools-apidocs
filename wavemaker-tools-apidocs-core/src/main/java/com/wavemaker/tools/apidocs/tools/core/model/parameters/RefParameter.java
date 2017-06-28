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
package com.wavemaker.tools.apidocs.tools.core.model.parameters;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RefParameter extends AbstractParameter {
    String ref;

    public RefParameter(String ref) {
        set$ref(ref);
    }

    public RefParameter asDefault(String ref) {
        this.set$ref("#/parameters/" + ref);
        return this;
    }

    public RefParameter description(String description) {
        this.setDescription(description);
        return this;
    }

    public String get$ref() {
        if (ref.startsWith("http"))
            return ref;
        else
            return "#/parameters/" + ref;
    }

    public void set$ref(String ref) {
        if (ref.indexOf("#/parameters/") == 0)
            this.ref = ref.substring("#/parameters/".length());
        else
            this.ref = ref;
    }

    @Override
    @JsonIgnore
    public boolean getRequired() {
        return required;
    }

    @JsonIgnore
    public String getSimpleRef() {
        if (ref.indexOf("#/parameters/") == 0)
            return ref.substring("#/parameters/".length());
        else
            return ref;
    }

    public static boolean isType(String type, String format) {
        if ("$ref".equals(type))
            return true;
        else return false;
    }
}