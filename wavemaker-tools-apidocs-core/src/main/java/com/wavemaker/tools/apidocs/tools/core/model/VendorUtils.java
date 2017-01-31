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
package com.wavemaker.tools.apidocs.tools.core.model;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 15/3/15
 */
public class VendorUtils {

    public static final String WM_EXTENSION_PREFIX = "x-WM-";


    public static Object getWMExtension(final ExtensibleEntity entity, final String key) {
        String extKey = extensionKey(key);
        return entity.getVendorExtensions().get(extKey);
    }

    public static void addWMExtension(final ExtensibleEntity entity, final String key, final Object value) {
        String extKey = extensionKey(key);
        entity.setVendorExtension(extKey, value);
    }

    public static String extensionKey(final String key) {
        return key.startsWith(WM_EXTENSION_PREFIX) ? key : (WM_EXTENSION_PREFIX + key);
    }
}
