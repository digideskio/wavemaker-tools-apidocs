/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.resolver;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import com.wavemaker.tools.apidocs.tools.core.model.Operation;
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
            final int index, final Class<?> type, final Annotation[] annotations,
            final Operation operation) {
        List<Parameter> parameters = new LinkedList<>();

        QueryParameter page = getDefaultParameterBuilder(index, type);
        page.setName("page");
        page.setDefaultValue("0");
        parameters.add(page);

        QueryParameter size = getDefaultParameterBuilder(index, type);
        size.setName("size");
        size.setDefaultValue("20");
        parameters.add(size);

        QueryParameter sort = getDefaultParameterBuilder(index, type);
        sort.setName("sort");
        sort.property(new StringProperty());
        parameters.add(sort);


        return parameters;
    }

    private QueryParameter getDefaultParameterBuilder(int index, Class<?> type) {
        QueryParameter builder = new QueryParameter();
        // builder.setResolver(type.getName());
//        builder.setIndex(index);
        builder.setRequired(false);
        builder.property(new IntegerProperty());
        builder.setEditable(false);
//        builder.setId(DataTypeUtil.getName(type));

        return builder;
    }
}
