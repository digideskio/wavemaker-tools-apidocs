package com.wavemaker.tools.apidocs.tools.core.deserializers.resolvers;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.wavemaker.tools.apidocs.tools.core.model.properties.*;

/**
 * @author <a href="mailto:nishanth.modhugu@wavemaker.com">Nishanth Reddy</a>
 * @since : 25/3/15 4:41 PM $
 */
public class CustomPropertyTypeIdResolver extends TypeIdResolverBase {

    private static final Map<String, Class<? extends Property>> subTypesMap = new HashMap<>();
    private static final Map<Class<? extends Property>, String> _subTypesMap = new HashMap<>();

    static {
        subTypesMap.put("object", MapProperty.class);
//        subTypesMap.put("object", ObjectProperty.class);
        subTypesMap.put("array", ArrayProperty.class);
        subTypesMap.put("boolean", BooleanProperty.class);
        subTypesMap.put("date", DateProperty.class);
        subTypesMap.put("date-time", DateTimeProperty.class);
        subTypesMap.put("number", DecimalProperty.class);
        subTypesMap.put("double", DoubleProperty.class);
        subTypesMap.put("float", FloatProperty.class);
        subTypesMap.put("integer", IntegerProperty.class);
        subTypesMap.put("integer", LongProperty.class);
        subTypesMap.put("file", FileProperty.class);
        subTypesMap.put("ref", RefProperty.class);
        subTypesMap.put("string", StringProperty.class);
        subTypesMap.put("uuid", UUIDProperty.class);

        _subTypesMap.put(MapProperty.class, "object");
//        _subTypesMap.put(ObjectProperty.class, "object");
        _subTypesMap.put(ArrayProperty.class, "array");
        _subTypesMap.put(BooleanProperty.class, "boolean");
        _subTypesMap.put(DateProperty.class, "date");
        _subTypesMap.put(DateTimeProperty.class, "date-time");
        _subTypesMap.put(DecimalProperty.class, "number");
        _subTypesMap.put(DoubleProperty.class, "double");
        _subTypesMap.put(FloatProperty.class, "float");
        _subTypesMap.put(IntegerProperty.class, "integer");
        _subTypesMap.put(LongProperty.class, "integer");
        _subTypesMap.put(FileProperty.class, "file");
        _subTypesMap.put(RefProperty.class, "ref");
        _subTypesMap.put(StringProperty.class, "string");
        _subTypesMap.put(UUIDProperty.class, "uuid");
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
        Class<? extends Property> subclass = subTypesMap.get(id);
        if (subclass == null)
            throw new RuntimeException("Unable to find type for id:" + id);
        return TypeFactory.defaultInstance().constructSpecializedType(baseType, subclass);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}
