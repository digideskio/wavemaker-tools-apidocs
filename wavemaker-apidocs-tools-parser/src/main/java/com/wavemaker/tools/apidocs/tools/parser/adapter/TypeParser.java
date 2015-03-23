/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.adapter;

import com.wavemaker.tools.apidocs.tools.core.model.Model;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public interface TypeParser {

    /**
     * It will parse the given {@link Class} type. If this {@link TypeParser} able to process given type returns true.
     * If this not able process given type it returns true, so filter chain continues next {@link TypeParser}.
     *
     * @param type     to be parsed to {@link Model}.
     * @return true if adapter able to process given type, else false.
     */
    TypeParserResponse parseType(final Class<?> type);
}