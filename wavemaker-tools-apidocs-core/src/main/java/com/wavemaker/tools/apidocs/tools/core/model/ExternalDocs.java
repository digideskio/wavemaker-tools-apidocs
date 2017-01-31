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
package com.wavemaker.tools.apidocs.tools.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Container for a <a href="https://github.com/swagger-api/swagger-spec/blob/master/versions/2.0.md#externalDocumentationObject">External
 * Documentation Object</a>.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalDocs {
    /**
     * A short description of the target documentation. GFM syntax can be used for rich text representation.
     */
    private String description;

    /**
     * Required. The URL for the target documentation. Value MUST be in the format of a URL.
     */
    private String url;

    public ExternalDocs() {
    }

    public ExternalDocs(String description, String url) {
        this.setDescription(description);
        this.setUrl(url);
    }

    public ExternalDocs description(String description) {
        this.setDescription(description);
        return this;
    }

    public ExternalDocs url(String url) {
        this.setUrl(url);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}