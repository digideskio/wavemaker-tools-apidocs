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
