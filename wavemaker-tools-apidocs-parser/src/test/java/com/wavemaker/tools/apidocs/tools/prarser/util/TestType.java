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
