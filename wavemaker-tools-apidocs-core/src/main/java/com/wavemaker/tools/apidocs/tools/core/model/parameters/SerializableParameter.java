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

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

@JsonDeserialize
public interface SerializableParameter extends Parameter {
    String getType();

    void setType(String type);

    Property getItems();

    void setItems(Property items);

    String getFormat();

    void setFormat(String format);

    String getCollectionFormat();

    void setCollectionFormat(String collectionFormat);

    List<String> getEnum();

    void setEnum(List<String> _enum);

    Integer getMaxLength();

    void setMaxLength(Integer maxLength);

    Integer getMinLength();

    void setMinLength(Integer minLength);

    String getPattern();

    void setPattern(String pattern);

    Boolean isUniqueItems();

    void setUniqueItems(Boolean uniqueItems);

    Number getMultipleOf();

    void setMultipleOf(Number multipleOf);

    Boolean isExclusiveMaximum();

    void setExclusiveMaximum(Boolean exclusiveMinimum);

    Boolean isExclusiveMinimum();

    void setExclusiveMinimum(Boolean exclusiveMinimum);

    Double getMaximum();

    void setMaximum(Double maximum);

    Double getMinimum();

    void setMinimum(Double minimum);

    Integer getMaxItems();

    void setMaxItems(Integer maxItems);

    Integer getMinItems();

    void setMinItems(Integer minItems);
}