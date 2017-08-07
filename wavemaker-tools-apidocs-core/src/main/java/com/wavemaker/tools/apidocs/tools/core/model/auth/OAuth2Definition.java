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
package com.wavemaker.tools.apidocs.tools.core.model.auth;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wavemaker.tools.apidocs.tools.core.model.ExtensibleEntity;
import com.wavemaker.tools.apidocs.tools.core.model.VendorUtils;

public class OAuth2Definition implements SecuritySchemeDefinition, ExtensibleEntity {
    private Map<String, Object> vendorExtensions = new HashMap<>();
    private static final String PROVIDER_ID = "PROVIDER_ID";


    private String type = "oauth2";
    private String authorizationUrl;
    private String tokenUrl;
    private String flow;
    private Map<String, String> scopes;

    public OAuth2Definition() {
    }

    public OAuth2Definition implicit(String authorizationUrl) {
        this.setAuthorizationUrl(authorizationUrl);
        this.setFlow("implicit");
        return this;
    }

    public OAuth2Definition password(String tokenUrl) {
        this.setTokenUrl(tokenUrl);
        this.setFlow("password");
        return this;
    }

    public OAuth2Definition application(String tokenUrl) {
        this.setTokenUrl(tokenUrl);
        this.setFlow("application");
        return this;
    }

    public OAuth2Definition accessCode(String authorizationUrl, String tokenUrl) {
        this.setTokenUrl(tokenUrl);
        this.setAuthorizationUrl(authorizationUrl);
        this.setFlow("accessCode");
        return this;
    }

    public OAuth2Definition scope(String name, String description) {
        this.addScope(name, description);
        return this;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public Map<String, String> getScopes() {
        return scopes;
    }

    public void setScopes(Map<String, String> scopes) {
        this.scopes = scopes;
    }

    public void addScope(String name, String description) {
        if (this.scopes == null)
            this.scopes = new HashMap<String, String>();
        this.scopes.put(name, description);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public String getProviderId() {
        return (String) VendorUtils.getWMExtension(this, PROVIDER_ID);
    }

    @JsonIgnore
    public void setProviderId(String providerId) {
        VendorUtils.addWMExtension(this, PROVIDER_ID, providerId);
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
