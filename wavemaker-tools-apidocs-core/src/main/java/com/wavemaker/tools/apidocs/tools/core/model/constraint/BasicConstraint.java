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
package com.wavemaker.tools.apidocs.tools.core.model.constraint;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Uday Shankar
 */
public class BasicConstraint extends AbstractConstraint {

    private String id;
    private String constraintType;
    private Map<String, Object> constraintParameters = new LinkedHashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    public Map<String, Object> getConstraintParameters() {
        return constraintParameters;
    }

    public void setConstraintParameters(Map<String, Object> constraintParameters) {
        this.constraintParameters = constraintParameters;
    }
}
