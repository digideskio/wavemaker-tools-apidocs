/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.prarser.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 26/11/14
 */
public class TestHelper {
    public static final Class<?> TEST_CLASS = TestType.class;

    public static final String INTEGER_FIELD = "anInt";
    public static final String CHAR_FIELD = "aChar";
    public static final String INT_ARRAY = "ints";
    public static final String GENERIC_ARRAY = "array";
    public static final String STRING_FIELD = "string";
    public static final String STRING_ARRAY = "strings";
    public static final String LIST_OF_STRING = "stringList";
    public static final String INTEGER_STRING_MAP = "integerStringMap";
    public static final String INTEGER_LIST_MAP = "integerListMap";
    public static final String GENERIC_LIST = "eList";
    public static final String WILD_CARD_LIST = "list";
    public static final String STRING_WILD_CARD_LIST = "listStrings";

    public static Field getField(String fieldName) throws NoSuchFieldException {
        return TEST_CLASS.getDeclaredField(fieldName);
    }

    public static Type getGenericType(String fieldName) throws NoSuchFieldException {
        return getField(fieldName).getGenericType();
    }
}
