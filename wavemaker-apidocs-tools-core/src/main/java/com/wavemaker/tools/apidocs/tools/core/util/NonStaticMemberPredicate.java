/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.util;

import java.lang.reflect.Member;

import com.google.common.base.Predicate;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 27/11/14
 */
public class NonStaticMemberPredicate<T extends Member> implements Predicate<T> {
    
    private static class NonStaticMemberPredicateHolder {
        private static final NonStaticMemberPredicate<?> INSTANCE = new NonStaticMemberPredicate<>();
    }

    public static NonStaticMemberPredicate getInstance() {
      return NonStaticMemberPredicateHolder.INSTANCE;
    }

    @Override
    public boolean apply(final T input) {
        return Utils.validateModifiers(input.getModifiers());
    }
}
