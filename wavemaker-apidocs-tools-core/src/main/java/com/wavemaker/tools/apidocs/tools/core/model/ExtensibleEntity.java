package com.wavemaker.tools.apidocs.tools.core.model;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 15/3/15
 */
public interface ExtensibleEntity {

    Object getWMExtension(String Key);

    void addWMExtension(String key, Object value);

}
