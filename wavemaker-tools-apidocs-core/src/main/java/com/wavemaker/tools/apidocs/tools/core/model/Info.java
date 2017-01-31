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
package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Info implements ExtensibleEntity {
    private static final String API_ID_EXT = "API_ID";
    private static final String ENTERPRISE_ID_EXT = "ENTERPRISE_ID";
    private static final String PROJECT_ID_EXT = "PROJECT_ID";
    private static final String SERVICE_TYPE_EXT = "SERVICE_TYPE";
    private static final String SERVICE_ID_EXT = "SERVICE_ID";


    private Map<String, Object> vendorExtensions = new HashMap<>();

    private String description;
    private String version;
    private String title;
    private String termsOfService;
    private Contact contact;
    private License license;


    public Info version(String version) {
        this.setVersion(version);
        return this;
    }

    public Info title(String title) {
        this.setTitle(title);
        return this;
    }

    public Info description(String description) {
        this.setDescription(description);
        return this;
    }

    public Info termsOfService(String termsOfService) {
        this.setTermsOfService(termsOfService);
        return this;
    }

    public Info contact(Contact contact) {
        this.setContact(contact);
        return this;
    }

    public Info license(License license) {
        this.setLicense(license);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTermsOfService() {
        return termsOfService;
    }

    public void setTermsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
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

    @JsonIgnore
    public void setApiId(String apiId) {
        VendorUtils.addWMExtension(this, API_ID_EXT, apiId);
    }

    public String getApiId() {
        return (String) VendorUtils.getWMExtension(this, API_ID_EXT);
    }

    @JsonIgnore
    public void setEnterpriseId(String enterpriseId) {
        VendorUtils.addWMExtension(this, ENTERPRISE_ID_EXT, enterpriseId);
    }

    public String getEnterpriseId() {
        return (String) VendorUtils.getWMExtension(this, ENTERPRISE_ID_EXT);
    }

    @JsonIgnore
    public void setProjectId(String projectId) {
        VendorUtils.addWMExtension(this, PROJECT_ID_EXT, projectId);
    }

    public String getProjectId() {
        return (String) VendorUtils.getWMExtension(this, PROJECT_ID_EXT);
    }

    @JsonIgnore
    public void setServiceType(String serviceType) {
        VendorUtils.addWMExtension(this, SERVICE_TYPE_EXT, serviceType);
    }

    public String getServiceType() {
        return (String) VendorUtils.getWMExtension(this, SERVICE_TYPE_EXT);
    }

    @JsonIgnore
    public void setServiceId(String serviceId) {
        VendorUtils.addWMExtension(this, SERVICE_ID_EXT, serviceId);
    }

    public String getServiceId() {
        return (String) VendorUtils.getWMExtension(this, SERVICE_ID_EXT);
    }

    public Info mergeWith(Info info) {
        if (info != null) {
            if (this.description == null)
                this.description = info.description;
            if (this.version == null)
                this.version = info.version;
            if (this.title == null)
                this.title = info.title;
            if (this.termsOfService == null)
                this.termsOfService = info.termsOfService;
            if (this.contact == null)
                this.contact = info.contact;
            if (this.license == null)
                this.license = info.license;
            if (this.vendorExtensions == null)
                this.vendorExtensions = info.vendorExtensions;
        }
        return this;
    }

}