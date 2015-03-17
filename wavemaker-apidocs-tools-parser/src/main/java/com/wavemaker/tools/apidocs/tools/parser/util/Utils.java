/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.util;

import java.lang.reflect.Modifier;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 24/11/14
 */
public class Utils {
    private static final String PATH_SEPARATOR = "/";

    public static boolean validateModifiers(int modifiers) {
        return !Modifier.isStatic(modifiers);
    }

    public static String combinePaths(String path1, String path2) {
        StringBuilder sb = new StringBuilder();

        if (!StringUtils.startsWith(path1, PATH_SEPARATOR)) {
            sb.append(PATH_SEPARATOR);
        }
        sb.append(path1);

        if (!StringUtils.endsWith(path1, PATH_SEPARATOR) && !StringUtils.startsWith(path2, PATH_SEPARATOR)) {
            sb.append(PATH_SEPARATOR);
        }
        sb.append(path2);

        return sb.toString();
    }

}
