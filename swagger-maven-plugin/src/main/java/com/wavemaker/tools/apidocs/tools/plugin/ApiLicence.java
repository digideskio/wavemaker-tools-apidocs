package com.wavemaker.tools.apidocs.tools.plugin;

import org.apache.maven.plugins.annotations.Parameter;

import com.wavemaker.tools.apidocs.tools.core.model.License;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/6/15
 */
public class ApiLicence {

    @Parameter
    private String name;

    @Parameter
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public License build() {
        License license = new License();

        license.name(name)
                .url(url);

        return license;
    }
}
