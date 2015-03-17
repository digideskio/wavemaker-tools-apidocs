package com.wavemaker.tools.apidocs.tools.core.model.swagger_2;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 15/3/15
 */
public interface ExtensibleEntity {

    Object getExtension(String Key);

    void addExtension(String key, Object value);

}
