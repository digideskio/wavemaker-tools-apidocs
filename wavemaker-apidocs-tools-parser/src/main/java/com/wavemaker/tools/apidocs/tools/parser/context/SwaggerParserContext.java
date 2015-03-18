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
import com.wavemaker.tools.apidocs.tools.parser.config.ApiParserConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.config.ModelFilterConfig;
import com.wavemaker.tools.apidocs.tools.parser.config.TypeAdaptersConfig;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class SwaggerParserContext {

    private static SwaggerParserContext instance;

    private final ApiParserConfiguration parserConfiguration;
    private TypesContext typesContext;
    private TypeParsersChain parsersChain;

    private SwaggerParserContext(final ApiParserConfiguration parserConfiguration) {
        this.parserConfiguration = parserConfiguration;
        parsersChain = TypeParserChainBuilder.defaultChain();
        typesContext = new TypesContext(parsersChain);
    }

    public static void initContext(ApiParserConfiguration configuration) {
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

    public ApiParserConfiguration getParserConfiguration() {
        return parserConfiguration;
    }

    public ParameterResolvers getResolversContext() {
        return parserConfiguration.getResolversContext();
    }

    public TypeAdaptersConfig getTypesAdapters() {
        return parserConfiguration.getTypeAdaptersConfig();
    }

    public ModelFilterConfig getModelFilterConfig() {
        return parserConfiguration.getModelFilterConfig();
    }

    public static SwaggerParserContext getInstance() {
        return instance;
    }
}
