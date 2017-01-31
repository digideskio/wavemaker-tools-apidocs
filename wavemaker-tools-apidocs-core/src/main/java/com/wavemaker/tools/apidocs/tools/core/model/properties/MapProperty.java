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

import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class MapProperty extends AbstractProperty implements Property {
    Property property;

    public MapProperty() {
        super.type = "object";
    }

    public MapProperty(Property property) {
        super.type = "object";
        this.property = property;
    }

    public MapProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public MapProperty additionalProperties(Property property) {
        this.setAdditionalProperties(property);
        return this;
    }

    public MapProperty description(String description) {
        this.setDescription(description);
        return this;
    }

    public Property getAdditionalProperties() {
        return property;
    }

    public void setAdditionalProperties(Property property) {
        this.property = property;
    }

    public static boolean isType(String type, String format) {
        if ("object".equals(type) && format == null)
            return true;
        return false;
    }
}