/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class ApiDocuments {

    private final Map<Class<?>, ApiDocument> apiDocumentMap;

    public ApiDocuments(final Map<Class<?>, ApiDocument> apiDocumentMap) {
        this.apiDocumentMap = apiDocumentMap;
    }

    public Map<Class<?>, ApiDocument> getApiDocuments() {
        return apiDocumentMap;
    }

    public ApiDocument getApiDocument(Class<?> classType) {
        return apiDocumentMap.get(classType);
    }

    public List<ApiDocument> getDocumentsAsList() {
        return new LinkedList<>(apiDocumentMap.values());
    }
}
