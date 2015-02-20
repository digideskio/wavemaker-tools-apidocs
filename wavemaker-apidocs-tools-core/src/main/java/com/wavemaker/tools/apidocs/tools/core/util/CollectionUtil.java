/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class CollectionUtil {
    public static <T> List<T> asList(T... args) {
        if (args != null) {
            Arrays.asList(args);
        }
        return Collections.emptyList();
    }

    public static <T> Set<T> asSet(T... args) {
        return new HashSet<>(asList(args));
    }

    public static <T> boolean isBlank(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotBlank(Collection<T> collection) {
        return !isBlank(collection);
    }
}
