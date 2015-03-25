package com.wavemaker.tools.apidocs.tools.core.deserializers.resolvers;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.wavemaker.tools.apidocs.tools.core.model.*;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 12:28 PM $
 */
public class CustomModelTypeIdResolver extends TypeIdResolverBase {

    private static final Map<String, Class<? extends Model>> subTypesMap = new HashMap<>();
    private static final Map<Class<? extends Model>, String> _subTypesMap = new HashMap<>();

    static {
        subTypesMap.put("array", ArrayModel.class);
        subTypesMap.put("allOf", ComposedModel.class);
        subTypesMap.put("object", ModelImpl.class);
        subTypesMap.put("ref", RefModel.class);

        _subTypesMap.put(ArrayModel.class, "array");
        _subTypesMap.put(ComposedModel.class, "allOf");
        _subTypesMap.put(ModelImpl.class, "object");
        _subTypesMap.put(RefModel.class, "ref");
    }

    private JavaType baseType;

    @Override
    public void init(JavaType baseType) {
        this.baseType = baseType;
    }

    @Override
    public String idFromValue(Object value) {
        return idFromValueAndType(value, value.getClass());
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return _subTypesMap.get(suggestedType);
    }

    @Override
    public String idFromBaseType() {
        return idFromValueAndType(null, baseType.getRawClass());
    }

    @Override
    public JavaType typeFromId(String id) {
        Class<? extends Model> subclass = subTypesMap.get(id);
        if (subclass == null)
            throw new RuntimeException("Unable to find type for id:" + id);
        return TypeFactory.defaultInstance().constructSpecializedType(baseType, subclass);

    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}
