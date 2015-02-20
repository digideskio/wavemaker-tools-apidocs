/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.parsers.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.wavemaker.tools.apidocs.tools.core.model.FoundTypesWrapper;
import com.wavemaker.tools.apidocs.tools.core.parser.BaseParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public abstract class AbstractFoundTypesParserContainer<T> implements BaseParser<FoundTypesWrapper<T>> {

    protected Set<Class<?>> newTypes;

    protected AbstractFoundTypesParserContainer() {
        this.newTypes = new HashSet<>();
    }

    @Override
    public FoundTypesWrapper<T> parse() {
        newTypes.clear();
        return new FoundTypesWrapper<>(parseInternal(), newTypes);
    }

    protected abstract T parseInternal();

    protected void addNewTypes(Collection<Class<?>> types) {
        newTypes.addAll(types);
    }

}
