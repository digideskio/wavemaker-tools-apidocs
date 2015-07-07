package com.wavemaker.tools.apidocs.tools.plugin;

import org.apache.maven.plugins.annotations.Parameter;

import com.wavemaker.tools.apidocs.tools.core.model.Contact;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/6/15
 */
public class ApiContact {

    @Parameter
    private String name;

    @Parameter
    private String url;

    @Parameter
    private String email;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getEmail() {
        return email;
    }

    public Contact build() {
        Contact contact = new Contact();

        contact.name(name)
                .url(url)
                .email(email);

        return contact;
    }
}
