/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.exceptions;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 19/11/14
 */
public class ModelParsingException extends RuntimeException{

    public ModelParsingException(final String message) {
        super(message);
    }

    public ModelParsingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
