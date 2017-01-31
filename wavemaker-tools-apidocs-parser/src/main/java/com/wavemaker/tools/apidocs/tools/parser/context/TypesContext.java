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
package com.wavemaker.tools.apidocs.tools.parser.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.base.Optional;
import com.wavemaker.tools.apidocs.tools.core.model.AbstractModel;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.RefModel;
import com.wavemaker.tools.apidocs.tools.parser.util.ContextUtil;
import com.wavemaker.tools.apidocs.tools.parser.util.DataTypeUtil;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 13/11/14
 */
public class TypesContext {

    private final Map<Class<?>, String> typesMap;
    private final Map<String, Class<?>> reverseTypesMap;

    private final Map<String, Model> definitionsMap;

    private Queue<Class<?>> pendingModels;

    TypesContext() {
        definitionsMap = new HashMap<>();
        reverseTypesMap = new HashMap<>();
        typesMap = new HashMap<>();
        pendingModels = new ConcurrentLinkedQueue<>();
    }

    protected void addType(Class<?> type, String name) {
        typesMap.put(type, name);
        reverseTypesMap.put(name, type);
    }

    public Optional<RefModel> parseModel(Class<?> type) {

        if (!pendingModels.contains(type)) { // checking whether it currently parsing, to avoid circular parsing.
            if (!definitionsMap.containsKey(getUniqueTypeName(type))) { // if model not exists, will parse
                pendingModels.add(type);
                Optional<Model> modelOptional = ContextUtil.getConfiguration().getModelScanner().scanModel(type);
                if (modelOptional.isPresent()) {
                    definitionsMap.put(getUniqueTypeName(type), modelOptional.get());
                }
                pendingModels.remove(type);
            }
        }
        // adding tag reference
        Model model = definitionsMap.get(getUniqueTypeName(type));
        if (model != null) {
            ((AbstractModel) model).addTag(ResourceParserContext.getContext().getTag());
        }
        return Optional.of(new RefModel(getUniqueTypeName(type)));
    }


    public Map<String, Model> getDefinitionsMap() {
        return definitionsMap;
    }

    /**
     * It will give you a unique name for each type.
     * <p/>
     * For example if User class defined in two packages. com.wavemaker.models.user com.wavemaker.entities.user
     * <p/>
     * When you call first type it will give you User, and for next type it will give entities.User
     *
     * @param type target class.
     * @return Unique name in the model context.
     */
    public String getUniqueTypeName(Class<?> type) {
        if (typesMap.containsKey(type)) { // type already cached.
            return typesMap.get(type);
        }
        // trying to create new type name.
        String uniqueName = findUniqueNameRecursively(type, 1);
        addType(type, uniqueName);

        return uniqueName;
    }

    private String findUniqueNameRecursively(Class<?> type, int attempt) {
        String name = DataTypeUtil.getName(type, attempt);
        if (reverseTypesMap.get(name) == null) {
            // not found, means its a new type.
            return name;
        } else {
            return findUniqueNameRecursively(type, ++attempt);
        }
    }

}
