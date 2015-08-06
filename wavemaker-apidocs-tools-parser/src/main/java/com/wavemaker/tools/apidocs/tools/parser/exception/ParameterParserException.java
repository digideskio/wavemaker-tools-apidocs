package com.wavemaker.tools.apidocs.tools.parser.exception;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 6/8/15
 */
public class ParameterParserException extends SwaggerParserException {
    public ParameterParserException(final String message) {
        super(message);
    }

    public ParameterParserException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
