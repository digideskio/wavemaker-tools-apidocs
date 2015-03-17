/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.adapter;

import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.Model;
import com.wavemaker.tools.apidocs.tools.parser.context.SwaggerParserContext;
import com.wavemaker.tools.apidocs.tools.parser.parser.ModelParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public class CustomTypeParser implements TypeParser {
    @Override
    public TypeParserResponse parseType(final Class<?> type) {
        Model model = null;
        boolean accepted = TypeParserResponse.NOT_ACCEPETED;
        if (SwaggerParserContext.getInstance().getTypesAdapters().isTypeHaveAdapter(type)) {
            ModelParser parser = SwaggerParserContext.getInstance().getTypesAdapters().getTypeAdapter(type);

            model = parser.parse();
            accepted = TypeParserResponse.ACCEPTED;
        }

        return new TypeParserResponse(accepted, model);
    }
}
