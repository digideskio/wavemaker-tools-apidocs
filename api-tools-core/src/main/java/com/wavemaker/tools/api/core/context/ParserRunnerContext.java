/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.core.context;

import com.wavemaker.tools.api.core.config.ApiParserConfiguration;
import com.wavemaker.tools.api.core.config.ModelFilterConfig;
import com.wavemaker.tools.api.core.config.TypeAdaptersConfig;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class ParserRunnerContext {

    private static ParserRunnerContext instance;

    private final ApiParserConfiguration parserConfiguration;

    private ParserRunnerContext(final ApiParserConfiguration parserConfiguration) {
        this.parserConfiguration = parserConfiguration;
    }

    public static void initContext(ApiParserConfiguration configuration) {
        instance = new ParserRunnerContext(configuration);
    }

    public static void destroyContext() {
        instance = null;
    }

    public ApiParserConfiguration getParserConfiguration() {
        return parserConfiguration;
    }

    public ParameterResolversContext getResolversContext() {
        return parserConfiguration.getResolversContext();
    }

    public TypeAdaptersConfig getTypesAdapters() {
        return parserConfiguration.getTypeAdaptersConfig();
    }

    public ModelFilterConfig getModelFilterConfig() {
        return parserConfiguration.getModelFilterConfig();
    }

    public static ParserRunnerContext getInstance() {
        return instance;
    }
}
