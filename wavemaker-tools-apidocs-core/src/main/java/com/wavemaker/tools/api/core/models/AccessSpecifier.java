/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
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
