/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tools.apidocs.tools.factory;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.runner.SwaggerParser;
import com.wavemaker.tools.apidocs.tools.spring.SpringSwaggerParser;
import com.wavemaker.tools.apidocs.tools.spring.resolver.MultiPartFileResolver;
import com.wavemaker.tools.apidocs.tools.spring.resolver.MultiPartRequestResolver;
import com.wavemaker.tools.apidocs.tools.spring.resolver.ServletMetaTypesResolver;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class ApiParserFactory {

    public static SwaggerParser newSpringParser(final SwaggerConfiguration.Builder builder) {

        builder.addParameterResolver(MultipartFile.class, new MultiPartFileResolver());
        builder.addParameterResolver(MultipartHttpServletRequest.class, new MultiPartRequestResolver());
        builder.addParameterResolver(HttpServletRequest.class, new ServletMetaTypesResolver());

        return newSwaggerParser(FrameworkType.SPRING, builder);
    }

    public static SwaggerParser newSwaggerParser(
            final FrameworkType frameworkType, final SwaggerConfiguration.Builder builder) {
        return newSwaggerParser(frameworkType, builder.build());
    }

    public static SwaggerParser newSwaggerParser(
            FrameworkType frameworkType, final SwaggerConfiguration configuration) {

        return new SpringSwaggerParser(configuration);
    }
}
