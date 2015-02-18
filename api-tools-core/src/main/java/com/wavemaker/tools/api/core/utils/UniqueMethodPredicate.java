/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.utils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Predicate;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 24/11/14
 */
public class UniqueMethodPredicate implements Predicate<Method> {

    private final Set<String> methodHashes;

    public UniqueMethodPredicate() {
        this.methodHashes = new HashSet<>();
    }

    @Override
    public boolean apply(final Method input) {
        String uniqueIdentifier = MethodUtils.getMethodUniqueIdentifierId(input);
        if (!methodHashes.contains(uniqueIdentifier)) {
            methodHashes.add(uniqueIdentifier);
            return true;
        }
        return false;
    }
}
