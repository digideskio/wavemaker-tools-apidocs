package com.wavemaker.tools.apidocs.tools.core.model.swagger_2.parameters;

import com.wavemaker.tools.apidocs.tools.core.model.swagger_2.Model;

public class BodyParameter extends AbstractParameter implements Parameter {
    Model schema;

    public BodyParameter() {
        super.setIn("body");
    }

    public BodyParameter schema(Model schema) {
        this.setSchema(schema);
        return this;
    }

    public BodyParameter description(String description) {
        this.setDescription(description);
        return this;
    }

    public BodyParameter name(String name) {
        this.setName(name);
        return this;
    }

    public void setSchema(Model schema) {
        this.schema = schema;
    }

    public Model getSchema() {
        return schema;
    }
}