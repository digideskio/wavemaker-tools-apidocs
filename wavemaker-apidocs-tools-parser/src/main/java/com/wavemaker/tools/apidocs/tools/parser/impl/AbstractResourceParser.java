/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.impl;

import java.util.Set;

import com.wavemaker.tools.apidocs.tools.core.annotation.WMAccessVisibility;
import com.wavemaker.tools.apidocs.tools.core.model.Path;
import com.wavemaker.tools.apidocs.tools.core.model.Resource;
import com.wavemaker.tools.apidocs.tools.parser.context.ResourceParserContext;
import com.wavemaker.tools.apidocs.tools.parser.parser.PathsParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.ResourceParser;
import com.wavemaker.tools.apidocs.tools.parser.util.ContextUtil;
import com.wordnik.swagger.annotations.Api;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public abstract class AbstractResourceParser implements ResourceParser {

    protected final Class<?> type;

    protected AbstractResourceParser(Class<?> type) {
        this.type = type;
    }

    @Override
    public Resource parse() {
        Resource resource = new Resource();

        if (type.isAnnotationPresent(Api.class)) {
            resource.setDescription(type.getAnnotation(Api.class).description());
        }
        resource.setName(ContextUtil.getUniqueName(type));
        resource.setFullyQualifiedName(type.getName());

        ResourceParserContext.getContext().setTag(resource.asTag().getName());
        ResourceParserContext.getContext().setResourcePath(getResourcePath());
        resource.setVersion(""); // XXX think it later?

        // For now it is commented, because we dropped BaseModel dependency while refactoring.
        // resource.setEditable(ParserRunnerContext.getInstance().getConfiguration().isEditable());

        //for end points use
        if (type.isAnnotationPresent(WMAccessVisibility.class)) {
            ResourceParserContext.getContext().setSpecifier(type.getAnnotation(WMAccessVisibility.class).value());
        }
        ResourceParserContext.getContext().setConsumes(getConsumes());
        ResourceParserContext.getContext().setProduces(getProduces());

        resource.setPathMap(getPathParser(type).parse());
        return resource;
    }

    protected abstract Set<String> getProduces();

    protected abstract Set<String> getConsumes();

    /**
     * It should return the path of the api.
     *
     * @return path of the api.
     */
    protected abstract String getResourcePath();

    /**
     * It should give the {@link PathsParser} instance.
     *
     * @param typeToParse class to parse for {@link Path}.
     * @return {@link PathsParser} instance.
     */
    protected abstract PathsParser getPathParser(Class<?> typeToParse);

}
