/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.adapter;

import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.parser.context.SwaggerParserContext;
import com.wavemaker.tools.apidocs.tools.parser.impl.SubstituteReflectionModelParser;
import com.wavemaker.tools.apidocs.tools.parser.parser.ModelParser;

/**
 * It will check if given {@link Class} have any substitute {@link Class} configured. If exists it will process that
 * one.
 *
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/11/14
 */
public class SubstituteTypeParser implements TypeParser {
    @Override
    public TypeParserResponse parseType(final Class<?> type) {

        boolean accepted = TypeParserResponse.NOT_ACCEPETED;
        Model model = null;
        if (SwaggerParserContext.getInstance().getTypesAdapters().isSubstituteExists(type)) {
            ModelParser parser = new SubstituteReflectionModelParser(
                    SwaggerParserContext.getInstance().getTypesAdapters().getSubstitute(type)
                    , type);
            accepted = TypeParserResponse.ACCEPTED;
            model = parser.parse();
        }

        return new TypeParserResponse(accepted, model);
    }
}
