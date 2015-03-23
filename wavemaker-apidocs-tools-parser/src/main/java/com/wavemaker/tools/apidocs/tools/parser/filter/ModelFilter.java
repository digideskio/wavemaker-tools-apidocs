/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.filter;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 1/12/14
 */
public interface ModelFilter {

    /**
     * evaluates the given type, if filtered returns true, else false.
     *
     * @param type to filter.
     * @return true if condition filtered else false.
     */
    boolean evaluate(Class<?> type);

}