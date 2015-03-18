/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
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

        return tag;
    }

}




