package com.wavemaker.tools.apidocs.tools.core.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavemaker.tools.apidocs.tools.core.model.properties.Property;
import com.wavemaker.tools.apidocs.tools.core.model.properties.RefProperty;

public class RefModel extends AbstractExtensibleEntity implements Model {

    private static final ObjectMapper objectMapper = new ObjectMapper(); // configuration shared across objects.
    private static final TypeReference<List<Property>> propertyListTypeRef = new TypeReference<List<Property>>() {
        public Type getType() {
            return new ParameterizedType() {
                @Override
                public Type[] getActualTypeArguments() {
                    return new Type[]{Model.class};
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
    // internally, the ref value is never fully qualified
    private String ref;
    private String description;
    private ExternalDocs externalDocs;
    private Map<String, Property> properties;
    private String example;

    public RefModel() {
    }

    public RefModel(String ref) {
        set$ref(ref);
    }

    public RefModel asDefault(String ref) {
        this.set$ref("#/definitions/" + ref);
        return this;
    }

    // not allowed in a $ref
    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Map<String, Property> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Property> properties) {
        this.properties = properties;
    }

    @JsonIgnore
    public String getSimpleRef() {
        if (ref.indexOf("#/definitions/") == 0)
            return ref.substring("#/definitions/".length());
        else
            return ref;
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
    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @JsonIgnore
    public ExternalDocs getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(ExternalDocs value) {
        externalDocs = value;
    }

    @JsonIgnore
    public void setTypeArguments(List<Model> properties) {
        addWMExtension(RefProperty.TYPE_ARGUMENTS_EXT, properties);
    }

    @JsonIgnore
    public List<Model> getTypeArguments() {
        Object typeArguments = getWMExtension(RefProperty.TYPE_ARGUMENTS_EXT);
        if (typeArguments == null) {
            return Collections.<Model>emptyList();
        } else {
            return objectMapper.convertValue(typeArguments, propertyListTypeRef);
        }
    }

    public Object clone() {
        RefModel cloned = new RefModel();
        cloned.ref = this.ref;
        cloned.description = this.description;
        cloned.properties = this.properties;
        cloned.example = this.example;
        cloned.vendorExtensions = this.vendorExtensions;

        return cloned;
    }
}