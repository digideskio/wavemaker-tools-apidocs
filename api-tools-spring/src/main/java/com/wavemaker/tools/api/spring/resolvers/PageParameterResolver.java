/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.spring.resolvers;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import com.wavemaker.tools.api.core.builders.ParameterBuilder;
import com.wavemaker.tools.api.core.builders.PrimitiveType;
import com.wavemaker.tools.api.core.models.FoundTypesWrapper;
import com.wavemaker.tools.api.core.models.Operation;
import com.wavemaker.tools.api.core.models.Parameter;
import com.wavemaker.tools.api.core.models.ParameterType;
import com.wavemaker.tools.api.core.resolvers.ParameterResolver;
import com.wavemaker.tools.api.core.utils.DataTypeUtil;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class PageParameterResolver implements ParameterResolver {
    @Override
    public FoundTypesWrapper<List<Parameter>> resolveParameter(
            final int index, final Class<?> type, final Annotation[] annotations,
            final Operation operation) {
        List<Parameter> parameters = new LinkedList<>();

        ParameterBuilder page = getDefaultParameterBuilder(index, type);
        page.setName("page");
        page.setDefaultValue("0");
        parameters.add(page.build());

        ParameterBuilder size = getDefaultParameterBuilder(index, type);
        size.setName("size");
        size.setDefaultValue("20");
        parameters.add(size.build());

        ParameterBuilder sort = getDefaultParameterBuilder(index, type);
        sort.setName("sort");
        sort.setType(PrimitiveType.STRING);
        parameters.add(sort.build());


        return new FoundTypesWrapper<>(parameters);
    }

    private ParameterBuilder getDefaultParameterBuilder(int index, Class<?> type) {
        ParameterBuilder builder = new ParameterBuilder();
        builder.setResolver(type.getName());
        builder.setIndex(index);
        builder.setParameterType(ParameterType.QUERY);
        builder.setRequired(false);
        builder.setType(PrimitiveType.INT_64);
        builder.setEditable(false);
        builder.setId(DataTypeUtil.getName(type));

        return builder;
    }
}
