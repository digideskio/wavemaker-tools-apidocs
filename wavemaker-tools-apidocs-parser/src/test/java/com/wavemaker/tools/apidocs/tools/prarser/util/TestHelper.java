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
