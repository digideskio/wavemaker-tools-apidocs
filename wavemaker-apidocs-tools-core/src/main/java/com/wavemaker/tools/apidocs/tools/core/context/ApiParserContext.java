/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.core.context;

import java.util.Collections;
import java.util.Set;

import com.wavemaker.tools.apidocs.tools.core.adapter.TypeParserChainBuilder;
import com.wavemaker.tools.apidocs.tools.core.adapter.TypeParsersChain;
import com.wavemaker.tools.apidocs.tools.core.model.AccessSpecifier;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class ApiParserContext {
    private static final ThreadLocal<ApiParserContext> parserContextTL = new ThreadLocal<>();

    private TypesContext typesContext;
    private TypeParsersChain parsersChain;

    private AccessSpecifier specifier;
    private Set<String> produces;
    private Set<String> consumes;

    private ApiParserContext() {
        typesContext = new TypesContext();
        parsersChain = TypeParserChainBuilder.defaultChain();

        produces = Collections.emptySet();
        consumes = Collections.emptySet();
        specifier = AccessSpecifier.APP_ONLY; // setting default one.
    }

    public TypesContext getTypesContext() {
        return typesContext;
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

    public TypeParsersChain getParsersChain() {
        return parsersChain;
    }

    public AccessSpecifier getSpecifier() {
        return specifier;
    }

    public void setSpecifier(final AccessSpecifier specifier) {
        this.specifier = specifier;
    }

    public static void initContext() {
        parserContextTL.set(new ApiParserContext());
    }

    public static void destroyContext() {
        parserContextTL.set(null);
    }

    public static ApiParserContext getContext() {
        return parserContextTL.get();
    }
}
