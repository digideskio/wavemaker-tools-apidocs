package com.wavemaker.tools.apidocs.tools.parser.impl;

import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.core.model.ModelImpl;
import com.wavemaker.tools.apidocs.tools.parser.parser.ModelParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 10/2/16
 */
public class ExcludedTypeModelParser implements ModelParser {

    @Override
    public Model parse() {
        // just returns model with empty properties.
        return new ModelImpl();
    }
}
