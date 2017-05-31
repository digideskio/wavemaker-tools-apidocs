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
package com.wavemaker.tools.apidocs.tools.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 31/5/17
 */
public class JsonUtils {

    private ObjectMapper objectMapper;

    private JsonUtils() {
        this.objectMapper = new ObjectMapper();
    }

    private static class JsonUtilsHolder {
        private static final JsonUtils INSTANCE = new JsonUtils();
    }

    public static JsonUtils getInstance() {
        return JsonUtilsHolder.INSTANCE;
    }

    public <T> T convertValue(Object from, TypeReference<T> toType) {
        return objectMapper.convertValue(from, toType);
    }

    public <T> T convertValue(Object from, Class<T> toType) {
        return objectMapper.convertValue(from, toType);
    }
}
