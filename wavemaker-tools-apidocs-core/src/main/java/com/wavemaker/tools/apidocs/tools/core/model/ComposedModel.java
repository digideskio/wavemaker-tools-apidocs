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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

public class ComposedModel extends AbstractModel {
    private List<Model> allOf = new ArrayList<>();
    private Model parent;
    private Model child;
    private List<RefModel> interfaces;
    private String description;
    private String example;

    public ComposedModel parent(Model model) {
        this.setParent(model);
        return this;
    }

    public ComposedModel child(Model model) {
        this.setChild(model);
        return this;
    }

    public ComposedModel interfaces(List<RefModel> interfaces) {
        this.setInterfaces(interfaces);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Property> getProperties() {
        return null;
    }

    public void setProperties(Map<String, Property> properties) {

    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public List<Model> getAllOf() {
        return allOf;
    }

    public void setAllOf(List<Model> allOf) {
        this.allOf = allOf;
    }

    @JsonIgnore
    public void setParent(Model model) {
        this.parent = model;
        if (!allOf.contains(model)) this.allOf.add(model);
    }

    public Model getParent() {
        return parent;
    }

    @JsonIgnore
    public void setChild(Model model) {
        this.child = model;
        if (!allOf.contains(model)) this.allOf.add(model);
    }

    public Model getChild() {
        return child;
    }

    @JsonIgnore
    public void setInterfaces(List<RefModel> interfaces) {
        this.interfaces = interfaces;
        for (RefModel model : interfaces)
            if (!allOf.contains(model)) allOf.add(model);
    }

    public List<RefModel> getInterfaces() {
        return interfaces;
    }


}