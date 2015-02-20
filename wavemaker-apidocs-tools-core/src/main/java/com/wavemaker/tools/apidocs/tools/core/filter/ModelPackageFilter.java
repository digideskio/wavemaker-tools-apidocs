/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.filter;

import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 1/12/14
 */
public class ModelPackageFilter implements ModelFilter {

    private final Pattern excludePackagesPattern;

    public ModelPackageFilter(final Set<String> excludedPackages, final boolean excludeSubPackages) {
        StringBuilder pattern = new StringBuilder();
        pattern.append("^(").append(StringUtils.join(excludedPackages, "|")).append(")");
        if (excludeSubPackages) {
            pattern.append("(.*)");
        }

        this.excludePackagesPattern = Pattern.compile(pattern.toString());
    }

    @Override
    public boolean apply(final Class<?> input) {
        return excludePackagesPattern.matcher(input.getPackage().getName()).matches();
    }

}
