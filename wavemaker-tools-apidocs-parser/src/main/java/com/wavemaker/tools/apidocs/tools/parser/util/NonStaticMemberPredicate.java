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
package com.wavemaker.tools.apidocs.tools.parser.util;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

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
        return !Modifier.isStatic(input.getModifiers());
    }
}
