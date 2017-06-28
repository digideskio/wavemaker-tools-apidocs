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

public class DateTimeProperty extends AbstractProperty {
    public DateTimeProperty() {
        super.type = "string";
        super.format = "date-time";
    }

    public DateTimeProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public DateTimeProperty example(String example) {
        this.setExample(example);
        return this;
    }

    public static boolean isType(String type, String format) {
        if ("string".equals(type) && "date-time".equals(format))
            return true;
        else return false;
    }
}