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
package com.wavemaker.tools.apidocs.tools.parser.context;

import java.util.Collections;
import java.util.Set;

import com.wavemaker.tools.api.core.models.AccessSpecifier;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class ResourceParserContext {
    private static final ThreadLocal<ResourceParserContext> parserContextTL = new ThreadLocal<>();

    private SwaggerParserContext swaggerParserContext;
    private AccessSpecifier specifier;
    private String resourcePath;
    private String tag;
    private Set<String> produces;
    private Set<String> consumes;

    private ResourceParserContext(final SwaggerParserContext swaggerParserContext) {
        produces = Collections.emptySet();
        consumes = Collections.emptySet();
        resourcePath = null;
        tag = null;
        this.swaggerParserContext = swaggerParserContext;
        specifier = AccessSpecifier.APP_ONLY; // setting default one.
    }


    public Set<String> getProduces() {
        return produces;
    }

    public void setProduces(final Set<String> produces) {
        this.produces = produces;
    }

    public Set<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(final Set<String> consumes) {
        this.consumes = consumes;
    }

    public AccessSpecifier getSpecifier() {
        return specifier;
    }

    public void setSpecifier(final AccessSpecifier specifier) {
        this.specifier = specifier;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(final String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public SwaggerParserContext getSwaggerParserContext() {
        return this.swaggerParserContext;
    }

    public static void initContext(final SwaggerParserContext context) {
        parserContextTL.set(new ResourceParserContext(context));
    }

    public static void destroyContext() {
        parserContextTL.set(null);
    }

    public static ResourceParserContext getContext() {
        return parserContextTL.get();
    }
}
