package com.wavemaker.tools.apidocs.tools.parser.exception;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 17/3/15
 */
public class PropertyParserException extends SwaggerParserException {

    public PropertyParserException(final String message) {
        super(message);
    }

    public PropertyParserException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
