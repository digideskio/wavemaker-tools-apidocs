/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
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