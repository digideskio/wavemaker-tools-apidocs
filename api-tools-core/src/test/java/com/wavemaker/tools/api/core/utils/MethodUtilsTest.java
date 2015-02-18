/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MethodUtilsTest {

    @Test
    public void testIsGetterMethod() throws Exception {
        assertTrue(MethodUtils.isGetterMethod("isValid"));
        assertTrue(MethodUtils.isGetterMethod("isEmpty"));
        assertTrue(MethodUtils.isGetterMethod("is"));
        assertTrue(MethodUtils.isGetterMethod("getValue"));
        assertTrue(MethodUtils.isGetterMethod("getUser"));
        assertTrue(MethodUtils.isGetterMethod("getThings"));
        assertTrue(MethodUtils.isGetterMethod("get"));
    }

    @Test
    public void testIsNotGetterMethod() throws Exception {
        assertFalse(MethodUtils.isGetterMethod("i"));
        assertFalse(MethodUtils.isGetterMethod("valid"));
        assertFalse(MethodUtils.isGetterMethod("emptyIs"));
        assertFalse(MethodUtils.isGetterMethod("g"));
        assertFalse(MethodUtils.isGetterMethod("ge"));
        assertFalse(MethodUtils.isGetterMethod("valueGet"));
        assertFalse(MethodUtils.isGetterMethod("userGet"));
        assertFalse(MethodUtils.isGetterMethod("someget"));
    }

    @Test
    public void testGetPropertyName() throws Exception {
        assertEquals(MethodUtils.DEFAULT_PROPERTY_NAME, MethodUtils.getPropertyName("is"));
        assertEquals(MethodUtils.DEFAULT_PROPERTY_NAME, MethodUtils.getPropertyName("get"));

        assertEquals("user", MethodUtils.getPropertyName("getUser"));
        assertEquals("user", MethodUtils.getPropertyName("isUser"));
        assertEquals("userIf", MethodUtils.getPropertyName("getUserIf"));
        assertEquals("userIf", MethodUtils.getPropertyName("isUserIf"));
    }

    @Test
    public void testGetAllMethods() throws Exception {

    }
}