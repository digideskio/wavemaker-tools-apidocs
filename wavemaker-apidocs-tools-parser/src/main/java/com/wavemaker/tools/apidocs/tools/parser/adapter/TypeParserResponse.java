package com.wavemaker.tools.apidocs.tools.parser.adapter;

import com.wavemaker.tools.apidocs.tools.core.model.Model;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 16/3/15
 */
public class TypeParserResponse {
    public static final boolean ACCEPTED = true;
    public static final boolean NOT_ACCEPETED = false;

    private final boolean accepted;
    private final Model model;

    public TypeParserResponse(final boolean accepted, final Model model) {
        this.model = model;
        this.accepted = accepted;
    }

    public Model getModel() {
        return model;
    }

    public boolean isAccepted() {
        return accepted;
    }

    @Override
    public String toString() {
        return "TypeParserResponse{" +
                "accepted=" + accepted +
                ", model=" + model +
                '}';
    }
}
