package com.wavemaker.tools.apidocs.tools.plugin;

import org.apache.maven.plugins.annotations.Parameter;

import com.wavemaker.tools.apidocs.tools.core.model.Contact;
import com.wavemaker.tools.apidocs.tools.core.model.Info;
import com.wavemaker.tools.apidocs.tools.core.model.License;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/6/15
 */
public class ApiInfo extends Info {

    @Parameter
    private String description;

    @Parameter
    private String version;

    @Parameter
    private String title;

    @Parameter
    private String termsOfService;

    @Parameter
    private ApiContact contact;

    @Parameter
    private ApiLicence license;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getTermsOfService() {
        return termsOfService;
    }

    @Override
    public Contact getContact() {
        return contact;
    }

    @Override
    public License getLicense() {
        return license;
    }
}
