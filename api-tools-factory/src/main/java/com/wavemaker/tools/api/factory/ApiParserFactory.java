/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.factory;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wavemaker.tools.api.core.config.ApiParserConfiguration;
import com.wavemaker.tools.api.core.config.ApiParserConfigurationBuilder;
import com.wavemaker.tools.api.core.runner.ApiParserRunner;
import com.wavemaker.tools.api.spring.SpringApiParserRunner;
import com.wavemaker.tools.api.spring.resolvers.MultiPartFileResolver;
import com.wavemaker.tools.api.spring.resolvers.MultiPartRequestResolver;
import com.wavemaker.tools.api.spring.resolvers.ServletMetaTypesResolver;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class ApiParserFactory {

    public static ApiParserRunner newSpringApiParserRunner(final ApiParserConfigurationBuilder builder) {

        builder.addParameterResolver(MultipartFile.class, MultiPartFileResolver.getInstance());
        builder.addParameterResolver(MultipartHttpServletRequest.class, MultiPartRequestResolver.getInstance());
        builder.addParameterResolver(HttpServletRequest.class, ServletMetaTypesResolver.getInstance());

        return newApiParserRunner(FrameworkType.SPRING, builder);
    }

    public static ApiParserRunner newApiParserRunner(
            final FrameworkType frameworkType, final ApiParserConfigurationBuilder builder) {
        return newApiParserRunner(frameworkType, builder.build());
    }

    public static ApiParserRunner newApiParserRunner(
            FrameworkType frameworkType, final ApiParserConfiguration configuration) {

        return new SpringApiParserRunner(configuration);
    }
}
