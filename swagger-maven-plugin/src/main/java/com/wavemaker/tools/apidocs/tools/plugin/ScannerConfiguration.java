package com.wavemaker.tools.apidocs.tools.plugin;

import java.util.List;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 8/6/15
 */
public class ScannerConfiguration {

    @Parameter(name = "include-packages")
    private List<String> includePackages;

    @Parameter(name = "exclude-packages")
    private List<String> excludePackages;

    @Parameter(name = "include-classes")
    private List<String> includeClasses;

    @Parameter(name = "exclude-classes")
    private List<String> excludeClasses;

    public List<String> getIncludePackages() {
        return includePackages;
    }

    public void setIncludePackages(final List<String> includePackages) {
        this.includePackages = includePackages;
    }

    public List<String> getExcludePackages() {
        return excludePackages;
    }

    public void setExcludePackages(final List<String> excludePackages) {
        this.excludePackages = excludePackages;
    }

    public List<String> getIncludeClasses() {
        return includeClasses;
    }

    public void setIncludeClasses(final List<String> includeClasses) {
        this.includeClasses = includeClasses;
    }

    public List<String> getExcludeClasses() {
        return excludeClasses;
    }

    public void setExcludeClasses(final List<String> excludeClasses) {
        this.excludeClasses = excludeClasses;
    }
}
