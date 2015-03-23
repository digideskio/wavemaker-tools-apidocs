/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.parser.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.wavemaker.tools.apidocs.tools.parser.builder.PrimitiveType;


/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 10/11/14
 */
public class DataTypeUtil {

    private static final Map<Class<?>, PrimitiveType> primitiveTypesMap = new HashMap<>();

    static {
        primitiveTypesMap.put(Short.TYPE, PrimitiveType.INT_32);
        primitiveTypesMap.put(Short.class, PrimitiveType.INT_32);

        primitiveTypesMap.put(Integer.TYPE, PrimitiveType.INT_32);
        primitiveTypesMap.put(Integer.class, PrimitiveType.INT_32);

        primitiveTypesMap.put(Long.TYPE, PrimitiveType.INT_64);
        primitiveTypesMap.put(Long.class, PrimitiveType.INT_64);

        primitiveTypesMap.put(Float.TYPE, PrimitiveType.FLOAT);
        primitiveTypesMap.put(Float.class, PrimitiveType.FLOAT);

        primitiveTypesMap.put(Double.TYPE, PrimitiveType.DOUBLE);
        primitiveTypesMap.put(Double.class, PrimitiveType.DOUBLE);
        primitiveTypesMap.put(BigDecimal.class, PrimitiveType.DOUBLE);

        primitiveTypesMap.put(Character.TYPE, PrimitiveType.STRING);
        primitiveTypesMap.put(Character.class, PrimitiveType.STRING);

        primitiveTypesMap.put(Boolean.TYPE, PrimitiveType.BOOLEAN);
        primitiveTypesMap.put(Boolean.class, PrimitiveType.BOOLEAN);

        primitiveTypesMap.put(Byte.TYPE, PrimitiveType.BYTE);
        primitiveTypesMap.put(Byte.class, PrimitiveType.BYTE);

        primitiveTypesMap.put(Void.TYPE, PrimitiveType.VOID);
        primitiveTypesMap.put(Void.class, PrimitiveType.VOID);


        // custom types
        primitiveTypesMap.put(String.class, PrimitiveType.STRING);
        primitiveTypesMap.put(File.class, PrimitiveType.FILE);
        primitiveTypesMap.put(Date.class, PrimitiveType.DATE);
    }


    /**
     * It will return {@link PrimitiveType} if given type is known else return null.
     *
     * @param type data type
     * @return {@link PrimitiveType} if type is in known list, else returns null.
     */
    public static PrimitiveType getPrimitiveType(Class<?> type) {
        if (type != null && type.isArray()) {
            return PrimitiveType.ARRAY;
        } else {
            return primitiveTypesMap.get(type);
        }
    }

    /**
     * @param type data type
     * @return true if type is known else return false.
     */
    public static boolean isPrimitiveType(Class<?> type) {
        return getPrimitiveType(type) != null;
    }

    /**
     * @param type data type
     * @return false if type is known else return true.
     */
    public static boolean isNotPrimitiveType(Class<?> type) {
        return !isPrimitiveType(type);
    }

    /**
     * It will parse the given {@link Class} as {@link Enum}, returns the list of values of given {@link Enum}
     *
     * @param type data type
     * @param <E>  data type
     * @return {@link List} of enum name Strings.
     * @throws IllegalArgumentException when given type is not an {@link Enum}.
     */
    public static <E> List<String> getEnumValues(Class<E> type) {
        if (isEnum(type)) {
            Class<Enum> enumClass = (Class<Enum>) type;
            List<String> values = new ArrayList<>(enumClass.getEnumConstants().length);
            for (Enum anEnum : enumClass.getEnumConstants()) {
                values.add(anEnum.name());
            }
            return values;
        } else {
            throw new IllegalArgumentException("Given type is not a Enum");
        }
    }

    /**
     * @param type data type
     * @return true if type is instance of {@link Enum}
     */
    public static boolean isEnum(Class<?> type) {
        return type.isEnum();
    }


    public static String getName(Class<?> type) {
        return getName(type, 1);
    }

    public static String getName(Class<?> type, int attempt) {
        if (attempt > 0) {
            String fullName = type.getName();
            String[] tokens = fullName.split("\\.");
            return StringUtils.join(tokens, '.', tokens.length - attempt, tokens.length);
        } else {
            throw new IllegalArgumentException("Attempt should be more than or equal 1");
        }
    }

}
