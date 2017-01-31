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
package com.wavemaker.tools.apidocs.tools.parser.scanner;

import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * Default implementation for the {@link ClassScanner}, it will returns all classes in current {@link ClassLoader}.
 *
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class AnyClassScanner implements ClassScanner {

    @Override
    public Set<Class<?>> classesToScan() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

        configurationBuilder.setUrls(ClasspathHelper.forClassLoader(Thread.currentThread().getContextClassLoader()));
//        configurationBuilder.setClassLoaders(new ClassLoader[]{Thread.currentThread().getContextClassLoader()});
        configurationBuilder.setScanners(new SubTypesScanner(false));
        Reflections reflections = new Reflections(configurationBuilder);

        return reflections.getSubTypesOf(Object.class);
    }
}
