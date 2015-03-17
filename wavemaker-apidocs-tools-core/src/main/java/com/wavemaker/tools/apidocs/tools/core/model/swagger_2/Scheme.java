package com.wavemaker.tools.apidocs.tools.core.model.swagger_2;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Scheme {
    HTTP, HTTPS, WS, WSS;

    // the below is required to write the enums as lowercase
    private static Map<String, Scheme> names = new HashMap<String, Scheme>();

    static {
        names.put("http", HTTP);
        names.put("https", HTTPS);
        names.put("ws", WS);
        names.put("wss", WSS);
    }

    @JsonCreator
    public static Scheme forValue(String value) {
        return names.get(value.toLowerCase());
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, Scheme> entry : names.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }
}