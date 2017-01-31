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

import java.util.List;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 8/6/15
 */
public class ScannerConfiguration {

    @Parameter(name = "include-packages")
    private List<String> includePackages;

    @Parameter(name = "exclude-packages")
    private List<String> excludePackages;

    @Parameter(name = "include-classes")
    private List<String> includeClasses;

    @Parameter(name = "exclude-classes")
    private List<String> excludeClasses;

    public List<String> getIncludePackages() {
        return includePackages;
    }

    public void setIncludePackages(final List<String> includePackages) {
        this.includePackages = includePackages;
    }

    public List<String> getExcludePackages() {
        return excludePackages;
    }

    public void setExcludePackages(final List<String> excludePackages) {
        this.excludePackages = excludePackages;
    }

    public List<String> getIncludeClasses() {
        return includeClasses;
    }

    public void setIncludeClasses(final List<String> includeClasses) {
        this.includeClasses = includeClasses;
    }

    public List<String> getExcludeClasses() {
        return excludeClasses;
    }

    public void setExcludeClasses(final List<String> excludeClasses) {
        this.excludeClasses = excludeClasses;
    }
}
