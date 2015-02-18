/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.test;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 26/11/14
 */
public class TestType<E> {
    private int anInt;
    private char aChar;
    private int[] ints;
    private E[] array;
    private String string;
    private String[] strings;
    private List<String> stringList;
    private Map<Integer, String> integerStringMap;
    private Map<Integer, List<String>> integerListMap;
    private List<E> eList;
    private List<?> list;
    private List<? extends String> listStrings;
}
