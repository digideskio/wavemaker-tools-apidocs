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
/**

 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.resolver;

import java.lang.annotation.Annotation;
import java.util.List;

import org.springframework.http.MediaType;

import com.google.common.collect.Lists;
import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.TypeInformation;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.AbstractParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.BodyParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.FormParameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.QueryParameter;
import com.wavemaker.tools.apidocs.tools.core.model.properties.FileProperty;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.core.utils.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.parser.resolver.ParameterResolver;
import com.wavemaker.tools.apidocs.tools.parser.util.Utils;
import com.wavemaker.tools.apidocs.tools.spring.parser.SpringParameterParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class MultiPartFileResolver implements ParameterResolver {

    public MultiPartFileResolver() {

    }

    @Override
    public List<Parameter> resolveParameter(
            final int index, final TypeInformation typeInformation, final Annotation[] annotations,
            final Operation operation) {
        SpringParameterParser parameterParser = new SpringParameterParser(index, typeInformation.getGenericType(),
                annotations);
        Parameter parameter = parameterParser.parse();

        if (!(parameter instanceof BodyParameter)) {
            final boolean array = Utils.isArray(typeInformation);
            Property property = new FileProperty();
            property.setRequired(true);
            if (parameter instanceof FormParameter) {
                if (array) {
                    ((FormParameter) parameter).items(property);
                } else {
                    ((FormParameter) parameter).property(property);
                }
            } else if (parameter instanceof QueryParameter) {
                if (array) {
                    ((QueryParameter) parameter).items(property);
                } else {
                    ((QueryParameter) parameter).property(property);
                }
            }
        }

        parameter.setName(parameter.getName());
        ((AbstractParameter) parameter).setResolver(typeInformation.getActualType().getName());
        // setting consumes to multi part form
        operation.setConsumes(Lists.newArrayList(MediaType.MULTIPART_FORM_DATA_VALUE));

        return CollectionUtil.asList(parameter);
    }
}
