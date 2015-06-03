package com.wavemaker.tools.apidocs.tools.core.exceptions;

/**
 * Created by sunilp on 21/5/15.
 */
public class SwaggerException extends RuntimeException {

    public SwaggerException(final String message) {
        super(message);
    }

    public SwaggerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SwaggerException(final Throwable cause) {
        super(cause);
    }
}
