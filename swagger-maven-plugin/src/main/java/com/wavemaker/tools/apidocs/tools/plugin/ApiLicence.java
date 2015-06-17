package com.wavemaker.tools.apidocs.tools.plugin;

import org.apache.maven.plugins.annotations.Parameter;

import com.wavemaker.tools.apidocs.tools.core.model.License;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/6/15
 */
public class ApiLicence extends License {

    @Parameter
    private String name;

    @Parameter
    private String url;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
