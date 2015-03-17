package com.wavemaker.tools.apidocs.tools.core.model.swagger_2;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 15/3/15
 */
public class AbstractExtensibleEntity implements ExtensibleEntity {

    public static final String EXTENSION_PREFIX = "x-";

    private Map<String, Object> vendorExtensions;

    public AbstractExtensibleEntity() {
        this.vendorExtensions = new HashMap<>();
    }

    @JsonIgnore
    @Override
    public Object getExtension(final String key) {
        String extKey = extensionKey(key);
        return vendorExtensions.get(extKey);
    }

    @JsonIgnore
    @Override
    public void addExtension(final String key, final Object value) {
        String extKey = extensionKey(key);
        vendorExtensions.put(extKey, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getVendorExtensions() {
        return vendorExtensions;
    }

    @JsonAnySetter
    public void setVendorExtension(String name, Object value) {
        if (name.startsWith(EXTENSION_PREFIX)) {
            vendorExtensions.put(name, value);
        }
    }

    private String extensionKey(final String key) {
        return key.startsWith(EXTENSION_PREFIX) ? key : (EXTENSION_PREFIX + key);
    }
}
