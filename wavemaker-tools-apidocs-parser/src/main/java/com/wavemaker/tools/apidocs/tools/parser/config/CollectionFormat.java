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
