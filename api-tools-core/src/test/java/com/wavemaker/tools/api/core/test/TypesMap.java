/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 26/11/14
 */
public enum TypesMap {
    INT(TestHelper.INTEGER_FIELD),
    INT_ARRAY(TestHelper.INT_ARRAY),
    CHAR(TestHelper.CHAR_FIELD),
    GENERIC_ARRAY(TestHelper.GENERIC_ARRAY),
    STRING(TestHelper.STRING_FIELD),
    STRING_ARRAY(TestHelper.STRING_ARRAY),
    STRING_LIST(TestHelper.LIST_OF_STRING),
    INTEGER_STRING_MAP(TestHelper.INTEGER_STRING_MAP),
    INTEGER_LIST_MAP(TestHelper.INTEGER_LIST_MAP),
    GENERIC_LIST(TestHelper.GENERIC_LIST),
    WILD_CARD_LIST(TestHelper.WILD_CARD_LIST),
    WILD_CARD_STRING_LIST(TestHelper.STRING_WILD_CARD_LIST);

    private final String fieldName;

    TypesMap(final String fieldName) {
        this.fieldName = fieldName;
    }

    public Field getField() throws NoSuchFieldException {
        return TestHelper.getField(fieldName);
    }

    public Type getType() throws NoSuchFieldException {
        return TestHelper.getGenericType(fieldName);
    }
}
