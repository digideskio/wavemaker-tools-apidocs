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
