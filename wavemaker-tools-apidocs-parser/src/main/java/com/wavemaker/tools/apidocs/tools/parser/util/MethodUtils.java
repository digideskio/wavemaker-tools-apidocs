/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tools.apidocs.tools.parser.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.reflections.ReflectionUtils;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 18/11/14
 */
public class MethodUtils {

    private static final Set<String> getterPrefixes = new HashSet<>();
    public static final String DEFAULT_PROPERTY_NAME = "arg";

    private static final Pattern getterPattern;

    static {
        getterPrefixes.add("get");
        getterPrefixes.add("is");

        getterPattern = Pattern.compile("^((" + StringUtils.join(getterPrefixes, "|") + ")(.*))");
    }

    public static Method getMethodForUniqueIdentifier(String uniqueId, Class<?> type) {
        Map<String, Method> identifierMap = getMethodUniqueIdentifierIdMap(type);

        if (identifierMap.containsKey(uniqueId)) {
            return identifierMap.get(uniqueId);
        }

        throw new IllegalArgumentException("There is no method associated with given unique key:" + uniqueId);
    }

    public static Map<String, Method> getMethodUniqueIdentifierIdMap(Class<?> type) {
        Map<String, Method> identifierMap = new LinkedHashMap<>();

        for (Method method : type.getDeclaredMethods()) {
            identifierMap.put(getMethodUniqueIdentifierId(method), method);
        }

        return identifierMap;
    }

    public static String generateMethodIdentifier(Method method) {
        final String uniqueIdentifierId = getMethodUniqueIdentifierId(method);

        return method.getDeclaringClass().getSimpleName() + "-" + uniqueIdentifierId;
    }

    public static String getMethodUniqueIdentifierId(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        for (Class parameterType : parameterTypes) {
            hashCodeBuilder.append(parameterType.getCanonicalName());
        }
        return method.getName() + "-" + parameterTypes.length + "-" + hashCodeBuilder.toHashCode();
    }

    public static Collection<Method> findGetterMethods(Class<?> classToScan) {
        return getAllNonStaticMethods(classToScan, new Predicate<Method>() {
            @Override
            public boolean apply(final Method input) {
                return isGetterMethod(input);
            }
        });
    }

    public static boolean isGetterMethod(Method method) {
        return ArrayUtils.isEmpty(method.getParameterTypes()) && isGetterMethod(method.getName());
    }

    public static boolean isGetterMethod(String methodName) {
        return getterPattern.matcher(methodName).matches();
    }

    /**
     * It will removes the getter part, gives the parameter name.
     * <p/>
     * For eg: method name is getUser, it will return 'user'.
     *
     * @param getterMethodName method name to scan.
     * @return parameter name
     * @throws IllegalArgumentException if given method is not a proper getter method.
     */
    public static String getPropertyName(String getterMethodName) throws IllegalArgumentException {
        if (isGetterMethod(getterMethodName)) {
            Matcher matcher = getterPattern.matcher(getterMethodName);
            String propName = "";
            if (matcher.find(0)) { // to fill the groups
                propName = matcher.group(3);
            }
            return StringUtils.isNotBlank(propName) ? StringUtils.uncapitalize(propName) : DEFAULT_PROPERTY_NAME;

        } else {
            throw new IllegalArgumentException("Given method is not a valid getter method:" + getterMethodName);
        }
    }

    /**
     * It will returns all Non static {@link Method}s of given {@link Class}.
     *
     * @param type {@link Class} to be scanned for methods.
     * @param predicates custom predicates to filter.
     * @return {@link Set} of filtered {@link Method}s.
     */
    public static Set<Method> getAllNonStaticMethods(Class<?> type, Predicate<? super Method>... predicates) {
        List<Predicate<? super Method>> predicateList = new LinkedList<>();

        predicateList.add(NonStaticMemberPredicate.getInstance());

        if (ArrayUtils.isNotEmpty(predicates)) {
            predicateList.addAll(Arrays.asList(predicates));
        }

        return ReflectionUtils.getMethods(type, Predicates.and(predicateList));

    }

}
