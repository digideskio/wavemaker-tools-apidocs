/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.adapter;

import java.util.List;

import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.Model;
import com.wavemaker.tools.apidocs.tools.parser.context.SwaggerParserContext;
import com.wavemaker.tools.apidocs.tools.parser.exception.ModelParsingException;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public class TypeParsersChain {
    private final List<TypeParser> typeParsers;

    public TypeParsersChain(final List<TypeParser> typeParsers) {
        this.typeParsers = typeParsers;
    }

    /**
     * It will parse the given type for {@link Model}.
     *
     * @param type to be parse
     */
    public Model processType(Class<?> type) {
        if (!SwaggerParserContext.getInstance().getModelFilterConfig().applyFilters(type)) {
            for (final TypeParser typeParser : typeParsers) {
                TypeParserResponse parserResponse = typeParser.parseType(type);
                if (parserResponse.isAccepted()) {
                    return parserResponse.getModel();
                }

            }
        }
        throw new ModelParsingException("No type adapter found for type:" + type);
    }
}
