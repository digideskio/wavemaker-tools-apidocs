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

public class SecurityDefinition {
    private String type;
    private Map<String, String> scopes;

    public SecurityDefinition() {
    }

    public SecurityDefinition(String type) {
        this.type = type;
    }

    public SecurityDefinition scope(String name, String description) {
        this.addScope(name, description);
        return this;
    }

    public void add(SecurityDefinition def) {
        if (def.scopes != null)
            for (String key : def.scopes.keySet()) {
                String value = def.scopes.get(key);
                System.out.println(key + " adding scope " + value);
                this.addScope(key, value);
            }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getScopes() {
        return scopes;
    }

    public void setScopes(Map<String, String> scopes) {
        this.scopes = scopes;
    }

    public void addScope(String name, String description) {
        if (this.scopes == null)
            this.scopes = new HashMap<String, String>();
        this.scopes.put(name, description);
    }
}