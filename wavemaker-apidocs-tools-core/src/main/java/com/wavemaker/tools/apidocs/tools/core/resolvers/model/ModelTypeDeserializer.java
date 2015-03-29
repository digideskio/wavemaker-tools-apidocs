package com.wavemaker.tools.apidocs.tools.core.resolvers.model;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.wavemaker.tools.apidocs.tools.core.model.ArrayModel;
import com.wavemaker.tools.apidocs.tools.core.model.ComposedModel;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.ModelImpl;
import com.wavemaker.tools.apidocs.tools.core.model.RefModel;
import com.wavemaker.tools.apidocs.tools.core.resolvers.CustomAsTypeDeserializer;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 27/3/15
 */
public class ModelTypeDeserializer extends CustomAsTypeDeserializer {
    public ModelTypeDeserializer(
            final JavaType bt, final TypeIdResolver idRes,
            final String typePropertyName, final boolean typeIdVisible, final Class<?> defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public ModelTypeDeserializer(final ModelTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    @Override
    protected Class<?> findSubType(final JsonNode node) {
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

    @Override
    protected TypeDeserializer newInstance(final BeanProperty property) {
        return new ModelTypeDeserializer(this, property);
    }

}
