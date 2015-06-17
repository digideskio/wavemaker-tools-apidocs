package com.wavemaker.tools.apidocs.tools.plugin;

import org.apache.maven.plugins.annotations.Parameter;

import com.wavemaker.tools.apidocs.tools.core.model.Contact;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/6/15
 */
public class ApiContact extends Contact {

    @Parameter
    private String name;

    @Parameter
    private String url;

    @Parameter
    private String email;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
