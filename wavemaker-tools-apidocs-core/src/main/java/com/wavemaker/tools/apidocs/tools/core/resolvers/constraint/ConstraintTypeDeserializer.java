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
package com.wavemaker.tools.apidocs.tools.core.resolvers.constraint;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wavemaker.tools.apidocs.tools.core.model.Constraint;
import com.wavemaker.tools.apidocs.tools.core.model.constraint.BasicConstraint;
import com.wavemaker.tools.apidocs.tools.core.model.constraint.RefConstraint;
import com.wavemaker.tools.apidocs.tools.core.resolvers.ExtendedStdDeserializer;

/**
 * @author Uday Shankar
 */
public class ConstraintTypeDeserializer extends ExtendedStdDeserializer {

    private static final Map<String, Class<? extends Constraint>> subTypesMap = new HashMap<>();

    static {
        subTypesMap.put("basic", BasicConstraint.class);
        subTypesMap.put("ref", RefConstraint.class);
    }

    public ConstraintTypeDeserializer() {
        super(Constraint.class); 
    }

    @Override
    protected Class<? extends Constraint> findSubType(final ObjectNode node) {
        Class<? extends Constraint> subType = null;
        if (node.get("type") != null) { // it won't come
            String type = node.get("type").asText();
            subType = subTypesMap.get(type);
        } else {
            subType = BasicConstraint.class;
        }
        return subType;
    }
}
