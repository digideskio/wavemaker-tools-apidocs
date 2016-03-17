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
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private String description;
    private Property schema;
    private Map<String, String> examples;
    private Map<String, Property> headers;

    public Response schema(Property property) {
        this.setSchema(property);
        return this;
    }

    public Response description(String description) {
        this.setDescription(description);
        return this;
    }

    public Response example(String type, String example) {
        if (examples == null) {
            examples = new HashMap<String, String>();
        }
        examples.put(type, example);
        return this;
    }

    public Response header(String name, Property property) {
        addHeader(name, property);
        return this;
    }

    public Response headers(Map<String, Property> headers) {
        this.headers = headers;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Property getSchema() {
        return schema;
    }

    public void setSchema(Property schema) {
        this.schema = schema;
    }

    public Map<String, String> getExamples() {
        return this.examples;
    }

    public void setExamples(Map<String, String> examples) {
        this.examples = examples;
    }

    public Map<String, Property> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Property> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, Property property) {
        if (this.headers == null)
            this.headers = new LinkedHashMap<String, Property>();
        this.headers.put(key, property);
    }
}
