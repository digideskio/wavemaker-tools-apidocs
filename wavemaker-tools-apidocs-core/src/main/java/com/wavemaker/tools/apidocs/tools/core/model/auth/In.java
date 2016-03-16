package com.wavemaker.tools.apidocs.tools.core.model.auth;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum In {
    HEADER, QUERY;

    private static Map<String, In> names = new HashMap<String, In>();

    static {
        names.put("header", HEADER);
        names.put("query", QUERY);
    }

    @JsonCreator
    public static In forValue(String value) {
        return names.get(value.toLowerCase());
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, In> entry : names.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }
}