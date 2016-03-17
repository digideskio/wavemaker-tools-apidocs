/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
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
package com.wavemaker.tools.apidocs.tools.spring;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.tools.apidocs.tools.core.model.Resource;
import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.runner.AnnotationSwaggerParser;
import com.wavemaker.tools.apidocs.tools.spring.parser.SpringResourceParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 12/11/14
 */
public class SpringSwaggerParser extends AnnotationSwaggerParser {

    public SpringSwaggerParser(final SwaggerConfiguration parserConfiguration) {
        super(parserConfiguration);
    }

    @Override
    protected Set<Class<? extends Annotation>> supportedRestAnnotations() {
        Set<Class<? extends Annotation>> annotations = new HashSet<>();

        annotations.add(Controller.class);
        annotations.add(RestController.class);

        return annotations;
    }

    @Override
    protected Resource parseRestClass(final Class<?> restClass) {
        SpringResourceParser parser = new SpringResourceParser(restClass);
        return parser.parse();
    }
}
