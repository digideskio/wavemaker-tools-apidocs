/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.scanner;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 * It will returns the classes from the given packages.
 *
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class FilterableClassScanner extends FilterBuilder implements ClassScanner {

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
