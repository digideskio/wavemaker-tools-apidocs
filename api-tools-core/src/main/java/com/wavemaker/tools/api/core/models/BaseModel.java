/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.models;

import java.util.Map;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 3/12/14
 */
public class BaseModel {
    private boolean isEditable = true;
    private Map<String, String> customProperties;

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(final boolean isEditable) {
        this.isEditable = isEditable;
    }

    public Map<String, String> getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(final Map<String, String> customProperties) {
        this.customProperties = customProperties;
    }
}
