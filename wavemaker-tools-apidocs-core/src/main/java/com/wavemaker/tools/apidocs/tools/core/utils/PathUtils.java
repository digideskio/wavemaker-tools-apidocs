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

import java.util.HashMap;
import java.util.Map;

import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.Path;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 15/7/15
 */
public class PathUtils {

    private enum Method {
        GET {
            @Override
            public Operation getOperation(final Path path) {
                return path.getGet();
            }
        },
        POST {
            @Override
            public Operation getOperation(final Path path) {
                return path.getPost();
            }
        },
        PUT {
            @Override
            public Operation getOperation(final Path path) {
                return path.getPut();
            }
        },
        DELETE {
            @Override
            public Operation getOperation(final Path path) {
                return path.getDelete();
            }
        },
        PATCH {
            @Override
            public Operation getOperation(final Path path) {
                return path.getPatch();
            }
        },
        OPTIONS {
            @Override
            public Operation getOperation(final Path path) {
                return path.getOptions();
            }
        };

        public abstract Operation getOperation(Path path);
    }

    private static final Map<String, Method> methodsMap = new HashMap<>();

    static {
        methodsMap.put("get", Method.GET);
        methodsMap.put("post", Method.POST);
        methodsMap.put("put", Method.PUT);
        methodsMap.put("delete", Method.DELETE);
        methodsMap.put("patch", Method.PATCH);
        methodsMap.put("options", Method.OPTIONS);
    }


    public static Operation getOperation(Path path, String method) {
        Method httpMethod = methodsMap.get(method.toLowerCase());
        return (httpMethod != null) ? httpMethod.getOperation(path) : null;
    }
}
