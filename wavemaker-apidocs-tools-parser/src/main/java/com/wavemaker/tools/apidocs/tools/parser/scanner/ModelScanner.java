package com.wavemaker.tools.apidocs.tools.parser.scanner;

import com.google.common.base.Optional;
import com.wavemaker.tools.apidocs.tools.core.model.Model;
import com.wavemaker.tools.apidocs.tools.parser.parser.ModelParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 31/3/15
 */
public interface ModelScanner {

    void addTypeAdapter(Class<?> type, ModelParser parser, final boolean includeSubTypes);

    void addSubstitute(Class<?> baseType, Class<?> substituteType, final boolean includeSubTypes);

    boolean filter(Class<?> type);

    Optional<Model> scanModel(Class<?> type);
}
