/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.resolvers;

import java.lang.annotation.Annotation;
import java.util.List;

import com.wavemaker.tools.api.core.models.FoundTypesWrapper;
import com.wavemaker.tools.api.core.models.Operation;
import com.wavemaker.tools.api.core.models.Parameter;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public interface ParameterResolver {

    FoundTypesWrapper<List<Parameter>> resolveParameter(
            int index, Class<?> type, Annotation[] annotations, final Operation operation);
}
