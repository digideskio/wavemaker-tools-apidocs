/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.parser;

import com.wavemaker.tools.apidocs.tools.core.model.Operation;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 10/11/14
 */
public interface MethodParser extends BaseParser<Operation> {

    /**
     * @return true if it is rest method.
     */
    boolean isRestMethod();

    /**
     * It will read the method and extracts the path of that method. This is use full separating endpoints.
     *
     * @return Path for given method.
     */
    String[] getPaths();

    /**
     * It will read the method and extracts the path of that method.
     *
     * @return Http Method
     */
    String[] getHttpMethods();
}
