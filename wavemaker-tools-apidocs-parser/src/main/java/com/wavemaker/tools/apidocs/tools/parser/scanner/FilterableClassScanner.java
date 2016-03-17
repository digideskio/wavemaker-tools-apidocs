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
package com.wavemaker.tools.apidocs.tools.parser.scanner;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * It will returns the classes from the given packages.
 *
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class FilterableClassScanner extends FilterableScanner implements ClassScanner {

    @Override
    public Set<Class<?>> classesToScan() {
        Set<Class<?>> classSet = new HashSet<>();

        ConfigurationBuilder configuration = new ConfigurationBuilder();
        configuration.setScanners(new SubTypesScanner(false));
//        configuration.addUrls(forContextClassLoader());
        configuration.filterInputsBy(this);
        configuration.addUrls(ClasspathHelper.forClassLoader());
        configuration.addUrls(ClasspathHelper.forJavaClassPath());

        Reflections reflections = new Reflections(configuration);
        classSet.addAll(reflections.getSubTypesOf(Object.class));

        return classSet;
    }
}
