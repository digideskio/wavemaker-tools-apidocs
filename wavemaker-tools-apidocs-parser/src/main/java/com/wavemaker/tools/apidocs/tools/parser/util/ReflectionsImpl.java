package com.wavemaker.tools.apidocs.tools.parser.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Configuration;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 16/6/15
 */
public class ReflectionsImpl extends Reflections {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionsImpl.class);

    public ReflectionsImpl(final Configuration configuration) {
        super(configuration);
    }

    @Override
    public <T> Set<Class<? extends T>> getSubTypesOf(final Class<T> type) {
        // Hack
        Iterable<String> types = store.getAll(SubTypesScanner.class.getSimpleName(), Arrays.asList(type.getName()));
        Set<Class<? extends T>> result = new HashSet<>();
        for (final String typeName : types) {
            Predicate<String> filter = configuration.getInputsFilter();
            if (filter != null && !filter.apply(typeName)) {
                continue; // if not filtered not adding to result
            }
            try {
                result.add((Class<? extends T>) ReflectionUtils.forName(typeName, configuration.getClassLoaders()));
            } catch (ReflectionsException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Error while getting type information {}, ignoring and proceed", typeName);
                }
            }
        }

        return result;
    }
}
