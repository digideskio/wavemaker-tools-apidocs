/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 24/11/14
 */
public class Utils {
    private static final String PATH_SEPARATOR = "/";

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

    public static List<Class<?>> getAllFilteredSuperTypes(Class<?> type) {
        List<Class<?>> superTypes = new LinkedList<>();
        if (type.getSuperclass() != null && !type.getSuperclass().equals(Object.class)) {
            superTypes.add(type.getSuperclass());
        }
        if (type.getInterfaces() != null) {
            for (final Class<?> aInterface : type.getInterfaces()) {
                superTypes.add(aInterface);
            }
        }

        return Lists.newLinkedList(Iterables.filter(superTypes, ContextUtil.getConfiguration().getModelFilters()));
    }


}