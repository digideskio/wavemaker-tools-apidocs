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
package com.wavemaker.tools.apidocs.tools.parser.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 23/3/15
 */
public enum CollectionFormat {
    CSV,
    SSV,
    TSV,
    PIPES,
    MULTI;

    // the below is required to write the enums as lowercase
    private static Map<String, CollectionFormat> names = new HashMap<>();

    static {
        names.put("csv", CSV);
        names.put("ssv", SSV);
        names.put("tsv", TSV);
        names.put("pipes", PIPES);
        names.put("multi", MULTI);
    }

    public static CollectionFormat forValue(String value) {
        return names.get(value.toLowerCase());
    }

}
