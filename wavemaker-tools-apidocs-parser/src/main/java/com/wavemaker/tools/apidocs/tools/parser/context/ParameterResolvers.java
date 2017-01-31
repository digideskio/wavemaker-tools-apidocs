/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
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
package com.wavemaker.tools.apidocs.tools.parser.context;

import java.util.HashMap;
import java.util.Map;

import com.wavemaker.tools.apidocs.tools.parser.resolver.ParameterResolver;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class ParameterResolvers {
    private final Map<Class<?>, ParameterResolver> resolverMap;

    public ParameterResolvers(final Map<Class<?>, ParameterResolver> resolverMap) {
        this.resolverMap = resolverMap;
    }

    public ParameterResolvers() {
        this(new HashMap<Class<?>, ParameterResolver>());
    }

    public void addResolver(Class<?> type, ParameterResolver resolver) {
        resolverMap.put(type, resolver);
    }

    public ParameterResolver getResolver(Class<?> type) {
        return resolverMap.get(type);
    }

    public boolean isResolverExist(Class<?> type) {
        return resolverMap.containsKey(type);
    }


}
