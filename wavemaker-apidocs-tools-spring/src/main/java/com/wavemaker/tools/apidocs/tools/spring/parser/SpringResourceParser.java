/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;

import com.wavemaker.tools.apidocs.tools.parser.impl.AbstractResourceParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.PathsParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class SpringResourceParser extends AbstractResourceParser {

    public SpringResourceParser(final Class<?> type) {
        super(type);
    }

    @Override
    protected Set<String> getProduces() {
        return new HashSet<>(Arrays.asList(type.getAnnotation(RequestMapping.class).produces()));
    }

    @Override
    protected Set<String> getConsumes() {
        return new HashSet<>(Arrays.asList(type.getAnnotation(RequestMapping.class).consumes()));
    }

    @Override
    protected String getResourcePath() {
        return type.getAnnotation(RequestMapping.class).value()[0];// fix this.
    }

    @Override
    protected PathsParser getPathParser(final Class<?> typeToParse) {
        return new SpringPathsParser(typeToParse);
    }

    @Override
    public boolean isRestApi() {
        return type.isAnnotationPresent(RequestMapping.class);
    }
}
