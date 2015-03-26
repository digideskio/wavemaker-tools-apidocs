package com.wavemaker.tools.apidocs.tools.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Info extends AbstractExtensibleEntity {
    private static final String API_ID_EXT = "API_ID";
    private static final String ENTERPRISE_ID_EXT = "ENTERPRISE_ID";
    private static final String PROJECT_ID_EXT = "PROJECT_ID";
    private static final String SERVICE_TYPE_EXT = "SERVICE_TYPE";
    private static final String SERVICE_ID_EXT = "SERVICE_ID";

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

    @JsonIgnore
    public void setApiId(String apiId) {
        addWMExtension(API_ID_EXT, apiId);
    }

    public String getApiId() {
        return (String) getWMExtension(API_ID_EXT);
    }

    @JsonIgnore
    public void setEnterpriseId(String enterpriseId) {
        addWMExtension(ENTERPRISE_ID_EXT, enterpriseId);
    }

    public String getEnterpriseId() {
        return (String) getWMExtension(ENTERPRISE_ID_EXT);
    }

    @JsonIgnore
    public void setProjectId(String projectId) {
        addWMExtension(PROJECT_ID_EXT, projectId);
    }

    public String getProjectId() {
        return (String) getWMExtension(PROJECT_ID_EXT);
    }

    @JsonIgnore
    public void setServiceType(String serviceType) {
        addWMExtension(SERVICE_TYPE_EXT, serviceType);
    }

    public String getServiceType() {
        return (String) getWMExtension(SERVICE_TYPE_EXT);
    }

    @JsonIgnore
    public void setServiceId(String serviceId) {
        addWMExtension(SERVICE_ID_EXT, serviceId);
    }

    public String getServiceId() {
        return (String) getWMExtension(SERVICE_ID_EXT);
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