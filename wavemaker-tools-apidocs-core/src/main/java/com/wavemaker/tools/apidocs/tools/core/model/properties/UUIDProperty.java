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

import java.util.List;

import com.wavemaker.tools.apidocs.tools.core.model.Xml;

public class UUIDProperty extends AbstractProperty {
    protected List<String> _enum;
    protected Integer minLength = null, maxLength = null;
    protected String pattern = null;

    public UUIDProperty() {
        super.type = "string";
        super.format = "uuid";
    }

    public UUIDProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }

    public UUIDProperty minLength(Integer minLength) {
        this.setMinLength(minLength);
        return this;
    }

    public UUIDProperty maxLength(Integer maxLength) {
        this.setMaxLength(maxLength);
        return this;
    }

    public UUIDProperty pattern(String pattern) {
        this.setPattern(pattern);
        return this;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public static boolean isType(String type, String format) {
        if ("string".equals(type) && "uuid".equals(format))
            return true;
        else return false;
    }
}