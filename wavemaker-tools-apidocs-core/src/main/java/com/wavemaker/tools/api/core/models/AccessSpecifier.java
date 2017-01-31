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
package com.wavemaker.tools.api.core.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
public enum AccessSpecifier {
    UNAVAILABLE,
    APP_ONLY,
    PRIVATE,
    PUBLIC;

    private static Map<String, AccessSpecifier> names = new HashMap<>();

    static {
        names.put("unavailable", UNAVAILABLE);
        names.put("app_only", APP_ONLY);
        names.put("private", PRIVATE);
        names.put("public", PUBLIC);
    }

    public static AccessSpecifier forValue(String value) {
        return value != null ? names.get(value.toLowerCase()) : null;
    }
}
