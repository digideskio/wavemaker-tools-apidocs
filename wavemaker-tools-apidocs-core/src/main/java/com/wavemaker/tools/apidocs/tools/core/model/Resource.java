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

import java.util.Map;


/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/11/14
 */
public class Resource {

    private String name;
    private String description;
    private Map<String, Path> pathMap;
    private String fullyQualifiedName;
    private String controllerName;
    private String version;


    /**
     * @return map of complete path vs {@link Path}
     */
    public Map<String, Path> getPathMap() {
        return pathMap;
    }

    /**
     * @param pathMap
     */
    public void setPathMap(Map<String, Path> pathMap) {
        this.pathMap = pathMap;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public void setFullyQualifiedName(final String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(final String controllerName) {
        this.controllerName = controllerName;
    }

    public void setTag(String tag) {
        if (pathMap != null) {
            for (final Map.Entry<String, Path> pathEntry : pathMap.entrySet()) {
                pathEntry.getValue().setTag(tag);
            }
        }
    }

    public Tag asTag() {
        Tag tag = new Tag();

        tag.setName(name);
        tag.setDescription(description);
        tag.setVersion(version);
        tag.setFullyQualifiedName(fullyQualifiedName);
        tag.setControllerName(controllerName);

        return tag;
    }

}




