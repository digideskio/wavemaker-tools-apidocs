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
