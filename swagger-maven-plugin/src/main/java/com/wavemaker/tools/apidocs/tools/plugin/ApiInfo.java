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
package com.wavemaker.tools.apidocs.tools.plugin;

import org.apache.maven.plugins.annotations.Parameter;

import com.wavemaker.tools.apidocs.tools.core.model.Info;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/6/15
 */
public class ApiInfo {

    @Parameter
    private String description;

    @Parameter
    private String version;

    @Parameter
    private String title;

    @Parameter
    private String termsOfService;

    @Parameter
    private ApiContact contact;

    @Parameter
    private ApiLicence license;

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public String getTitle() {
        return title;
    }

    public String getTermsOfService() {
        return termsOfService;
    }

    public ApiContact getContact() {
        return contact;
    }

    public ApiLicence getLicense() {
        return license;
    }

    public Info build() {
        Info info = new Info();

        info.description(description)
                .version(version)
                .title(title)
                .termsOfService(termsOfService);
        if (contact != null) {
            info.contact(contact.build());
        }

        if (license != null) {
            info.license(license.build());
        }

        return info;
    }
}
