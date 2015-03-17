/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
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
