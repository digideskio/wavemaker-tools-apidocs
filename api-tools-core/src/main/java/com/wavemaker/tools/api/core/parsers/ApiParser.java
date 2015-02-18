/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.parsers;

import com.wavemaker.tools.api.core.models.ApiDocument;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
public interface ApiParser extends BaseParser<ApiDocument> {

    /**
     * It will check for the required Annotations in controller.
     *
     * @return true if it is a rest Api.
     */
    boolean isRestApi();
}
