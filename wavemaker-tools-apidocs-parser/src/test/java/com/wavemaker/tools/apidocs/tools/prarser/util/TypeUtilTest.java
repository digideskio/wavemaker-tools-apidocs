/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
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

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.wavemaker.tools.apidocs.tools.core.model.TypeInformation;
import com.wavemaker.tools.apidocs.tools.parser.util.TypeUtil;

public class TypeUtilTest {

    @Test
    public void testExtractTypeInformation() throws Exception {
        TypeInformation typeInfo = TypeUtil.extractTypeInformation(List.class);
        Assert.assertNotNull(typeInfo);
        Assert.assertSame(List.class, typeInfo.getActualType());
        Assert.assertEquals(0, typeInfo.getTypeArguments().size());
        Assert.assertFalse(typeInfo.isArray());
    }

    @Test
    public void testInt() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.INT.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(int.class, typeInformation.getActualType());
        Assert.assertEquals(0, typeInformation.getTypeArguments().size());
        Assert.assertFalse(typeInformation.isArray());
    }

    @Test
    public void testString() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.STRING.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(String.class, typeInformation.getActualType());
        Assert.assertEquals(0, typeInformation.getTypeArguments().size());
        Assert.assertFalse(typeInformation.isArray());
    }

    @Test
    public void testChar() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.CHAR.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(char.class, typeInformation.getActualType());
        Assert.assertEquals(0, typeInformation.getTypeArguments().size());
        Assert.assertFalse(typeInformation.isArray());
    }

    @Test
    public void testIntArray() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.INT_ARRAY.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(int[].class, typeInformation.getActualType());
        Assert.assertEquals(1, typeInformation.getTypeArguments().size());
        Assert.assertTrue(typeInformation.isArray());
    }

    @Test
    public void testGenericArray() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.GENERIC_ARRAY.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(Object[].class, typeInformation.getActualType());
        Assert.assertEquals(1, typeInformation.getTypeArguments().size());
        Assert.assertTrue(typeInformation.isArray());
    }

    @Test
    public void testStringArray() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.STRING_ARRAY.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(String[].class, typeInformation.getActualType());
        Assert.assertEquals(1, typeInformation.getTypeArguments().size());
        Assert.assertTrue(typeInformation.isArray());
    }

    @Test
    public void testStringList() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.STRING_LIST.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(List.class, typeInformation.getActualType());
        Assert.assertEquals(1, typeInformation.getTypeArguments().size());
        Assert.assertFalse(typeInformation.isArray());
    }

    @Test
    public void testIntStringMap() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.INTEGER_STRING_MAP.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(Map.class, typeInformation.getActualType());
        Assert.assertEquals(2, typeInformation.getTypeArguments().size());
        Assert.assertFalse(typeInformation.isArray());
    }

    @Test
    public void testIntListMap() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.INTEGER_LIST_MAP.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(Map.class, typeInformation.getActualType());
        Assert.assertEquals(2, typeInformation.getTypeArguments().size());
        Assert.assertFalse(typeInformation.isArray());
    }

    @Test
    public void testGenericList() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.GENERIC_LIST.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(List.class, typeInformation.getActualType());
        Assert.assertEquals(1, typeInformation.getTypeArguments().size());
        Assert.assertTrue(typeInformation.getTypeArguments().remove(Object.class)); // if exists, removes and returns
        // true
        Assert.assertFalse(typeInformation.isArray());
    }

    @Test
    public void testWildCardList() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.WILD_CARD_LIST.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(List.class, typeInformation.getActualType());
        Assert.assertEquals(1, typeInformation.getTypeArguments().size());
        Assert.assertTrue(typeInformation.getTypeArguments().remove(Object.class)); // if exists, removes and returns
        // true
        Assert.assertFalse(typeInformation.isArray());
    }

    @Test
    public void testWildCardStringList() throws Exception {
        TypeInformation typeInformation = TypeUtil.extractTypeInformation(TypesMap.WILD_CARD_STRING_LIST.getType());
        Assert.assertNotNull(typeInformation);
        Assert.assertSame(List.class, typeInformation.getActualType());
        Assert.assertEquals(1, typeInformation.getTypeArguments().size());
        Assert.assertTrue(typeInformation.getTypeArguments().remove(Object.class));
//        Assert.assertTrue(typeInformation.getTypeArguments().remove(String.class)); // if exists, removes and returns
        // true
        Assert.assertFalse(typeInformation.isArray());
    }
}