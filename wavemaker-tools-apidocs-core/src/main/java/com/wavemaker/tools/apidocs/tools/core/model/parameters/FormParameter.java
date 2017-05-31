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
package com.wavemaker.tools.apidocs.tools.core.model.parameters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.VendorUtils;
import com.wavemaker.tools.apidocs.tools.core.utils.JsonUtils;

public class FormParameter extends AbstractSerializableParameter<FormParameter> {

    public static final String FORM_DATA = "formData";
    public static final String SCHEMA_EXT = "SCHEMA";

    public FormParameter() {
        super.setIn(FORM_DATA);
    }

    @Override
    protected String getDefaultCollectionFormat() {
        return "multi";
    }

    @JsonIgnore
    public void setSchema(Model model) {
        VendorUtils.addWMExtension(this, SCHEMA_EXT, model);
    }

    public Model getSchema() {
        final Object model = VendorUtils.getWMExtension(this, SCHEMA_EXT);

        if (model == null) {
            return null;
        } else {
            return JsonUtils.getInstance().convertValue(model, Model.class);
        }
    }
}
