package com.wavemaker.tools.apidocs.tools.plugin;

import java.util.List;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 8/6/15
 */
public class ScannerConfiguration {

    private List<String> includePackages;

    private List<String> excludePackages;

    private List<String> includeClasses;

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
