/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.context;

import java.util.Collections;
import java.util.Set;

import com.wavemaker.tools.apidocs.tools.core.model.AccessSpecifier;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class ResourceParserContext {
    private static final ThreadLocal<ResourceParserContext> parserContextTL = new ThreadLocal<>();

    private AccessSpecifier specifier;
    private String resourcePath;
    private String tag;
    private Set<String> produces;
    private Set<String> consumes;

    private ResourceParserContext() {
        produces = Collections.emptySet();
        consumes = Collections.emptySet();
        resourcePath = null;
        tag = null;
        specifier = AccessSpecifier.APP_ONLY; // setting default one.
    }


    public Set<String> getProduces() {
        return produces;
    }

    public void setProduces(final Set<String> produces) {
        this.produces = produces;
    }

    public Set<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(final Set<String> consumes) {
        this.consumes = consumes;
    }

    public AccessSpecifier getSpecifier() {
        return specifier;
    }

    public void setSpecifier(final AccessSpecifier specifier) {
        this.specifier = specifier;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(final String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public static void initContext() {
        parserContextTL.set(new ResourceParserContext());
    }

    public static void destroyContext() {
        parserContextTL.set(null);
    }

    public static ResourceParserContext getContext() {
        return parserContextTL.get();
    }
}
