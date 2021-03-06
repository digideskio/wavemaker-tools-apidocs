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
package com.wavemaker.tools.apidocs.tools.parser.context;

import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/11/14
 */
public class SwaggerParserContext {

    private final SwaggerConfiguration configuration;
    private TypesContext typesContext;

    public SwaggerParserContext(final SwaggerConfiguration configuration) {
        this.configuration = configuration;
        typesContext = new TypesContext();
    }

    public TypesContext getTypesContext() {
        return typesContext;
    }

    public SwaggerConfiguration getConfiguration() {
        return configuration;
    }
}
