package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecurityRequirement {
    private String name;
    private String type;
    private List<String> scopes;

    public SecurityRequirement() {
    }

    public SecurityRequirement(String name) {
        this.name = name;
    }

    public SecurityRequirement scope(String scope) {
        this.addScope(scope);
        return this;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public void addScope(String scope) {
        if (scopes == null)
            scopes = new ArrayList<String>();
        scopes.add(scope);
    }
}