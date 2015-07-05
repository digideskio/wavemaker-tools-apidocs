package com.wavemaker.tools.apidocs.tools.core.model.auth;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class ApiKeyAuthDefinition implements SecuritySchemeDefinition {
    private Map<String, Object> vendorExtensions = new HashMap<>();

    private String type = "apiKey";
    private String name;
    private In in;

    public ApiKeyAuthDefinition() {
    }

    public ApiKeyAuthDefinition(String name, In in) {
        super();
        this.setName(name);
        this.setIn(in);
    }

    public ApiKeyAuthDefinition name(String name) {
        this.setName(name);
        return this;
    }

    public ApiKeyAuthDefinition in(In in) {
        this.setIn(in);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public In getIn() {
        return in;
    }

    public void setIn(In in) {
        this.in = in;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonAnyGetter
    public Map<String, Object> getVendorExtensions() {
        return vendorExtensions;
    }

    @JsonAnySetter
    public void setVendorExtension(String name, Object value) {
        if (name.startsWith("x-")) {
            vendorExtensions.put(name, value);
        }
    }
}
