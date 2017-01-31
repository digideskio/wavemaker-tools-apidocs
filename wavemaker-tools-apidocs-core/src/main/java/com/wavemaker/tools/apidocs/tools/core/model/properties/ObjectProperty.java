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

public class ObjectProperty extends AbstractProperty implements Property {
    public ObjectProperty() {
        super.type = "object";
    }

    public ObjectProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public ObjectProperty example(String example) {
        this.setExample(example);
        return this;
    }

    //TODO: implement additional formats
    public static boolean isType(String type, String format) {
        if ("object".equals(type))
            return true;
        else return false;
    }
}