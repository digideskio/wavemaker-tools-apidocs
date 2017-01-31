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
package com.wavemaker.tools.apidocs.tools.parser.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 24/11/14
 */
public class Utils {
    private static final String PATH_SEPARATOR = "/";

    public static String combinePaths(String path1, String path2) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotBlank(path1)) {
            if (!path1.startsWith(PATH_SEPARATOR)) {
                sb.append(PATH_SEPARATOR);
            }
            if (path1.endsWith(PATH_SEPARATOR)) {
                path1 = path1.substring(0, path1.length() - 1);
            }
            sb.append(path1);
        }

        if (StringUtils.isNotBlank(path2)) {
            if (!path2.startsWith(PATH_SEPARATOR)) {
                sb.append(PATH_SEPARATOR);
            }
            sb.append(path2);
        }


        return sb.toString();
    }

    public static List<Class<?>> getAllFilteredSuperTypes(Class<?> type) {
        List<Class<?>> superTypes = new LinkedList<>();
        if (type.getSuperclass() != null) {
            // All classes in java overrides Object, we need to ignore it to avoid making all models as Composed.
            if (!type.getSuperclass().equals(Object.class)) {
                if (ContextUtil.getConfiguration().getModelScanner().filter(type.getSuperclass())) {
                    superTypes.add(type.getSuperclass());
                }
            }
        }
        if (type.getInterfaces() != null) {
            for (final Class<?> aInterface : type.getInterfaces()) {
                if (ContextUtil.getConfiguration().getModelScanner().filter(aInterface)) {
                    superTypes.add(aInterface);
                }
            }
        }

        return superTypes;
    }


}
