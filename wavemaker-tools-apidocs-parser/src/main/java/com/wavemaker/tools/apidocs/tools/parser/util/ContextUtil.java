package com.wavemaker.tools.apidocs.tools.parser.util;

import com.google.common.base.Optional;
import com.wavemaker.tools.apidocs.tools.core.model.RefModel;
import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.context.ResourceParserContext;
import com.wavemaker.tools.apidocs.tools.parser.context.SwaggerParserContext;
import com.wavemaker.tools.apidocs.tools.parser.context.TypesContext;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 21/3/15
 */
public class ContextUtil {

    public static String getUniqueName(Class<?> type) {
        return getTypesContext().getUniqueTypeName(type);
    }

    public static Optional<RefModel> parseModel(Class<?> type) {
        return getTypesContext().parseModel(type);
    }

    public static SwaggerConfiguration getConfiguration() {
        return getSwaggerParserContext().getConfiguration();
    }

    public static TypesContext getTypesContext() {
        return getSwaggerParserContext().getTypesContext();
    }

    public static SwaggerParserContext getSwaggerParserContext() {
        return ResourceParserContext.getContext().getSwaggerParserContext();
    }
}
