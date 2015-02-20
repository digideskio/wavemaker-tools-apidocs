/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.Collection;
import java.util.Collections;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public class FoundTypesWrapper<T> {

    private final T model;
    private final Collection<Class<?>> foundTypes;

    public FoundTypesWrapper(final T model, final Collection<Class<?>> foundTypes) {
        this.model = model;
        this.foundTypes = foundTypes;
    }

    public FoundTypesWrapper(final T model) {
        this(model, Collections.EMPTY_LIST);
    }

    public T getModel() {
        return model;
    }

    public Collection<Class<?>> getFoundTypes() {
        return foundTypes;
    }
}
