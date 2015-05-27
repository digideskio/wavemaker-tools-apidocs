package com.wavemaker.tools.apidocs.tools.core.model.properties;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RefProperty extends AbstractProperty implements Property {
    private static final String TYPE_ARGUMENTS_EXT = "TYPE_ARGUMENTS";

    private static final ObjectMapper objectMapper = new ObjectMapper(); // configuration shared across objects.
    private static final TypeReference<List<Property>> propertyListTypeRef = new TypeReference<List<Property>>() {
        public Type getType() {
            return new ParameterizedType() {
                @Override
                public Type[] getActualTypeArguments() {
                    return new Type[]{Property.class};
                }

                @Override
                public Type getRawType() {
                    return List.class;
                }

                @Override
                public Type getOwnerType() {
                    return null;
                }
            };
        }
    };

    String ref;

    public RefProperty() {
        super.type = "ref";
    }

    public RefProperty(String ref) {
        super.type = "ref";
        set$ref(ref);
    }

    public RefProperty asDefault(String ref) {
        this.set$ref("#/definitions/" + ref);
        return this;
    }

    public RefProperty description(String description) {
        this.setDescription(description);
        return this;
    }

    @Override
    @JsonIgnore
    public String getType() {
        return this.type;
    }

    @Override
    @JsonIgnore
    public void setType(String type) {
        this.type = type;
    }

    public String get$ref() {
        if (ref.startsWith("http"))
            return ref;
        else
            return "#/definitions/" + ref;
    }

    public void set$ref(String ref) {
        if (ref.indexOf("#/definitions/") == 0)
            this.ref = ref.substring("#/definitions/".length());
        else
            this.ref = ref;
    }

    @JsonIgnore
    public String getSimpleRef() {
        if (ref.indexOf("#/definitions/") == 0)
            return ref.substring("#/definitions/".length());
        else
            return ref;
    }

    public static boolean isType(String type, String format) {
        if ("$ref".equals(type))
            return true;
        else return false;
    }

    @JsonIgnore
    public void setTypeArguments(List<Property> properties) {
        addWMExtension(TYPE_ARGUMENTS_EXT, properties);
    }

    @JsonIgnore
    public List<Property> getTypeArguments() {
        Object typeArguments = getWMExtension(TYPE_ARGUMENTS_EXT);
        if (typeArguments == null) {
            return Collections.<Property>emptyList();
        } else {
            return objectMapper.convertValue(typeArguments, propertyListTypeRef);
        }
    }
}