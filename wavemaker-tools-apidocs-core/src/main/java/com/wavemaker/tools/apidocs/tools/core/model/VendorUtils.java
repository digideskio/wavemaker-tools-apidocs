package com.wavemaker.tools.apidocs.tools.core.model;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 15/3/15
 */
public class VendorUtils {

    public static final String WM_EXTENSION_PREFIX = "x-WM-";


    public static Object getWMExtension(final ExtensibleEntity entity, final String key) {
        String extKey = extensionKey(key);
        return entity.getVendorExtensions().get(extKey);
    }

    public static void addWMExtension(final ExtensibleEntity entity, final String key, final Object value) {
        String extKey = extensionKey(key);
        entity.setVendorExtension(extKey, value);
    }

    private static String extensionKey(final String key) {
        return key.startsWith(WM_EXTENSION_PREFIX) ? key : (WM_EXTENSION_PREFIX + key);
    }
}
