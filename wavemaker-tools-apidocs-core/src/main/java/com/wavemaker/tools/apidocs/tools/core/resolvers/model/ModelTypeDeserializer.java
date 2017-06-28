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
package com.wavemaker.tools.apidocs.tools.core.resolvers.model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wavemaker.tools.apidocs.tools.core.model.ArrayModel;
import com.wavemaker.tools.apidocs.tools.core.model.ComposedModel;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.ModelImpl;
import com.wavemaker.tools.apidocs.tools.core.model.RefModel;
import com.wavemaker.tools.apidocs.tools.core.resolvers.ExtendedStdDeserializer;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 27/3/15
 */
public class ModelTypeDeserializer extends ExtendedStdDeserializer<Model> {

    public ModelTypeDeserializer() {
        super(Model.class);
    }

    @Override
    protected Class<? extends Model> findSubType(final ObjectNode node) {
        Class<? extends Model> subType;

        if (node.get("$ref") != null) {
            subType = RefModel.class;
        } else if (node.get("allOf") != null) {
            subType = ComposedModel.class;
        } else {
            subType = ModelImpl.class; // default
            if (node.get("type") != null) {
                String type = node.get("type").asText();
                if (type != null && !type.equals("null")) {
                    if (type.equals("array")) {
                        subType = ArrayModel.class;
                    }
                }
            }
        }

        return subType;
    }
}
