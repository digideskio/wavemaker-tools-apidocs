/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.scanner;

import java.util.Set;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public interface ClassScanner {

    /**
     * It should returns the list of classes to scan for the Rest Apis.
     *
     * @return Set of {@link Class} to scan.
     */
    Set<Class<?>> classesToScan();
}
