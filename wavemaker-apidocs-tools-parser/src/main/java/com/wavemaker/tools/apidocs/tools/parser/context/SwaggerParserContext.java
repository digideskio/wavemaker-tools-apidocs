/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.context;

import com.wavemaker.tools.apidocs.tools.parser.adapter.TypeParserChainBuilder;
import com.wavemaker.tools.apidocs.tools.parser.adapter.TypeParsersChain;
import com.wavemaker.tools.apidocs.tools.parser.config.ModelFilters;
import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.config.TypeAdapters;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class SwaggerParserContext {

    private static SwaggerParserContext instance;

    private final SwaggerConfiguration configuration;
    private TypesContext typesContext;
    private TypeParsersChain parsersChain;

    private SwaggerParserContext(final SwaggerConfiguration configuration) {
        this.configuration = configuration;
        parsersChain = TypeParserChainBuilder.defaultChain();
        typesContext = new TypesContext(parsersChain);
    }

    public static void initContext(SwaggerConfiguration configuration) {
        instance = new SwaggerParserContext(configuration);
    }

    public TypesContext getTypesContext() {
        return typesContext;
    }

    public TypeParsersChain getParsersChain() {
        return parsersChain;
    }

    public static void destroyContext() {
        instance = null;
    }

    public SwaggerConfiguration getConfiguration() {
        return configuration;
    }

    public ParameterResolvers getResolversContext() {
        return configuration.getParameterResolvers();
    }

    public TypeAdapters getTypesAdapters() {
        return configuration.getTypeAdapters();
    }

    public ModelFilters getModelFilters() {
        return configuration.getModelFilters();
    }

    public static SwaggerParserContext getInstance() {
        return instance;
    }
}
