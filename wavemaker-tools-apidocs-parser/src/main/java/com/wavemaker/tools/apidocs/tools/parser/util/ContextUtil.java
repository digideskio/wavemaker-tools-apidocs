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
