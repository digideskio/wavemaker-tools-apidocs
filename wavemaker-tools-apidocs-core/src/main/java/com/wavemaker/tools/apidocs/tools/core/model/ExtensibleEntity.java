package com.wavemaker.tools.apidocs.tools.core.model;

import java.util.Map;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 15/3/15
 */
public interface ExtensibleEntity {

    Map<String, Object> getVendorExtensions();

    void setVendorExtension(String key, Object value);

}
