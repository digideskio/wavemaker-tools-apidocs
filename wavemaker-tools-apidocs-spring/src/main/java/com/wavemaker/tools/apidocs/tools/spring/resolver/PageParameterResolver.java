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
package com.wavemaker.tools.apidocs.tools.spring.resolver;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import com.wavemaker.tools.apidocs.tools.core.model.Operation;
import com.wavemaker.tools.apidocs.tools.core.model.TypeInformation;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.Parameter;
import com.wavemaker.tools.apidocs.tools.core.model.parameters.QueryParameter;
import com.wavemaker.tools.apidocs.tools.core.model.properties.IntegerProperty;
import com.wavemaker.tools.apidocs.tools.core.model.properties.StringProperty;
import com.wavemaker.tools.apidocs.tools.parser.resolver.ParameterResolver;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class PageParameterResolver implements ParameterResolver {
    @Override
    public List<Parameter> resolveParameter(
            final int index, final TypeInformation typeInformation, final Annotation[] annotations,
            final Operation operation) {
        List<Parameter> parameters = new LinkedList<>();

        QueryParameter page = getDefaultParameterBuilder();
        page.setName("page");
        page.setDefaultValue("0");
        page.setResolver(typeInformation.getActualType().getName());
        parameters.add(page);

        QueryParameter size = getDefaultParameterBuilder();
        size.setName("size");
        size.setDefaultValue("20");
        page.setResolver(typeInformation.getActualType().getName());
        parameters.add(size);

        QueryParameter sort = getDefaultParameterBuilder();
        sort.setName("sort");
        sort.property(new StringProperty());
        page.setResolver(typeInformation.getActualType().getName());
        parameters.add(sort);


        return parameters;
    }

    private QueryParameter getDefaultParameterBuilder() {
        QueryParameter queryParameter = new QueryParameter();
        // builder.setResolver(type.getName());
//        builder.setIndex(index);
        queryParameter.setRequired(false);
        queryParameter.property(new IntegerProperty());
        queryParameter.setEditable(false);
//        builder.setId(DataTypeUtil.getName(type));

        return queryParameter;
    }
}
