/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.parsers.impl;

import java.util.LinkedList;
import java.util.Set;

import com.wavemaker.tools.apidocs.tools.core.annotation.WMAccessVisibility;
import com.wavemaker.tools.apidocs.tools.core.context.ApiParserContext;
import com.wavemaker.tools.apidocs.tools.core.context.ParserRunnerContext;
import com.wavemaker.tools.apidocs.tools.core.model.ApiDocument;
import com.wavemaker.tools.apidocs.tools.core.model.EndPoint;
import com.wavemaker.tools.apidocs.tools.core.parser.ApiParser;
import com.wavemaker.tools.apidocs.tools.core.parser.EndPointsParser;
import com.wavemaker.tools.apidocs.tools.core.util.DataTypeUtil;
import com.wordnik.swagger.annotations.Api;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public abstract class AbstractApiParser implements ApiParser {

    protected final Class<?> type;

    protected AbstractApiParser(Class<?> type) {
        this.type = type;
    }

    @Override
    public ApiDocument parse() {
        ApiDocument document = new ApiDocument();

        if (type.isAnnotationPresent(Api.class)) {
            document.setDescription(type.getAnnotation(Api.class).description());
        }
        document.setName(DataTypeUtil.getUniqueClassName(type));
        document.setFullyQualifiedName(type.getName());

        document.setRelativePath(getRelativePath());
        document.setBaseURL(ParserRunnerContext.getInstance().getParserConfiguration().getBaseUrl());
        document.setVersion(""); // XXX think it later?
        
        // For now it is commented, because we dropped BaseModel dependency while refactoring.
        // document.setEditable(ParserRunnerContext.getInstance().getParserConfiguration().isEditable());

        //for end points use
        if (type.isAnnotationPresent(WMAccessVisibility.class)) {
            ApiParserContext.getContext().setSpecifier(type.getAnnotation(WMAccessVisibility.class).value());
        }
        ApiParserContext.getContext().setConsumes(getConsumes());
        ApiParserContext.getContext().setProduces(getProduces());

        document.setEndPoints(getEndPointParser(type).parse());
        document.setModels(new LinkedList<>(ApiParserContext.getContext().getParsersChain().getModelMap().values()));
        return document;
    }

    protected abstract Set<String> getProduces();

    protected abstract Set<String> getConsumes();

    /**
     * It should return the path of the api.
     *
     * @return path of the api.
     */
    protected abstract String getRelativePath();

    /**
     * It should give the {@link EndPointsParser} instance.
     *
     * @param typeToParse class to parse for {@link EndPoint}.
     * @return {@link EndPointsParser} instance.
     */
    protected abstract EndPointsParser getEndPointParser(Class<?> typeToParse);

}
