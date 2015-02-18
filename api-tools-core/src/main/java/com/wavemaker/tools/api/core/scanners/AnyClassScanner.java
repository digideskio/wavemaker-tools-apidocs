/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.scanners;

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
