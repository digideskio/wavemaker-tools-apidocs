/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.scanner;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

/**
 * It will returns the classes from the given packages.
 *
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class PackageClassScanner implements ClassScanner {

    private final static String PACKAGE_DELIM = ".";

    private final String[] packages;

    public PackageClassScanner(String... packages) {
        if (ArrayUtils.isNotEmpty(packages)) {
            this.packages = new String[packages.length];
            for (int i = 0; i < packages.length; i++) {
                this.packages[i] = StringUtils.appendIfMissing(packages[i], PACKAGE_DELIM);
            }
        } else {
            this.packages = new String[0];
        }
    }

    @Override
    public Set<Class<?>> classesToScan() {
        Set<Class<?>> classSet = new HashSet<>();

        for (String aPackage : packages) {
            Reflections reflections = new Reflections(aPackage, new SubTypesScanner(false));
            classSet.addAll(reflections.getSubTypesOf(Object.class));
        }

        return classSet;
    }
}
