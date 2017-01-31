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
package com.wavemaker.tools.apidocs.tools.parser.builder;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 10/11/14
 */
public enum PrimitiveType {
    INT_32("integer", "int32"),
    INT_64("integer", "int64"),
    FLOAT("number", "float"),
    DOUBLE("number", "double"),
    STRING("string", "string"),
    BYTE("byte", "byte"),
    BOOLEAN("boolean", "boolean"),
    DATE("date", "date"),
    DATE_TIME("dateTime", "date-time"),
    ARRAY("array", "array"),
    FILE("file", "file"),
    VOID("void", "void");

    private final String type;
    private final String format;

    PrimitiveType(final String type, final String format) {
        this.type = type;
        this.format = format;
    }

    public String getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }
}
