package com.wavemaker.tools.apidocs.tools.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.wavemaker.tools.apidocs.tools.core.exceptions.SwaggerException;
import com.wavemaker.tools.apidocs.tools.core.model.*;

/**
 * Created by sunilp on 21/5/15.
 * <p/>
 * This class deals with CRUD operations on Swagger Objects.
 * Eg: Merging two swagger Objects,
 * Deleting operation based on single class in swagger.
 */
public class SwaggerUtils {

    enum PathMethod {
        Get, Put, Post, Delete, Patch, Options;
    }

    /**
     * This Api used to merge two swagger objects.
     *
     * @param swaggerOne : First swagger instance
     * @param swaggerTwo : Second swagger instance
     * @return
     */
    public static Swagger addOrUpdate(Swagger swaggerOne, Swagger swaggerTwo) {
        Objects.requireNonNull(swaggerOne, "swagger should not be null");
        Objects.requireNonNull(swaggerTwo, "swagger should not be null");
        return addOrUpdate(new Swagger[]{swaggerOne,swaggerTwo});
    }


    /**
     * This Api used to merge two or more swagger objects.
     *
     * @param swaggers : list of swagger object to merge.
     * @return
     */
    public static Swagger addOrUpdate(Swagger... swaggers) {
        validateMetaData(swaggers);
        Swagger swagger = swaggers[0];
        for (int i = 1; i < swaggers.length; i++) {
            updateTags(swagger, swaggers[i]);
            updatePaths(swagger, swaggers[i]);
            updateSchemes(swagger, swaggers[i]);
            updateDefinitions(swagger, swaggers[i]);
        }
        return swagger;
    }

    private static void updateTags(Swagger swaggerOne, Swagger swaggerTwo) {
        Map<String, Tag> tags = new HashMap<>();
        for (Tag tag : swaggerOne.getTags()) {
            tags.put(tag.getName(), tag);
        }
        for (Tag tag : swaggerTwo.getTags()) {
            tags.put(tag.getName(), tag);
        }
        swaggerOne.setTags(new ArrayList<>(tags.values()));
    }

    private static void updatePaths(Swagger swaggerOne, Swagger swaggerTwo) {
        Map<String, Path> paths = swaggerOne.getPaths();
        for (Map.Entry<String, Path> entry : swaggerTwo.getPaths().entrySet()) {
            if (!paths.keySet().contains(entry.getKey())) {
                paths.put(entry.getKey(), entry.getValue());
            } else {
                Path pathOne = paths.get(entry.getKey());
                mergePaths(pathOne, entry.getValue(), PathMethod.values());

            }
        }
        swaggerOne.setPaths(paths);
    }

    private static void mergePaths(Path pathOne, Path pathTwo, PathMethod... pathMethods) {
        try {
            for (PathMethod pathMethod : pathMethods) {
                Method methodOne = pathOne.getClass().getMethod("get" + pathMethod.toString());
                Method methodTwo = pathTwo.getClass().getMethod("get" + pathMethod.toString());
                Operation operationOne = (Operation) methodOne.invoke(pathOne);
                Operation operationTwo = (Operation) methodTwo.invoke(pathTwo);
                if (operationOne == null && operationTwo != null) {
                    pathOne.getClass().getMethod("set" + pathMethod.toString()).invoke(pathOne, operationTwo);
                }
                if (operationOne != null && operationTwo != null) {
                    List<String> tagsOne = operationOne.getTags();
                    List<String> tagsTwo = operationTwo.getTags();
                    for (String tag : tagsTwo) {
                        if (!tagsOne.contains(tag)) {
                            tagsOne.add(tag);
                        }
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new SwaggerException("Failed to update path in swagger");
        }
    }

    private static void updateSchemes(Swagger swaggerOne, Swagger swaggerTwo) {
        Map<String, Scheme> schemes = new HashMap<>();
        for (Scheme scheme : swaggerOne.getSchemes()) {
            schemes.put(scheme.toValue(), scheme);
        }
        for (Scheme scheme : swaggerTwo.getSchemes()) {
            schemes.put(scheme.toValue(), scheme);
        }
        swaggerOne.setSchemes(new ArrayList<>(schemes.values()));
    }

    private static void updateDefinitions(Swagger swaggerOne, Swagger swaggerTwo) {
        swaggerOne.getDefinitions().putAll(swaggerTwo.getDefinitions());
    }

    private static void validateMetaData(Swagger... swaggers) {
        Set<String> versions = new HashSet<>();
        Set<String> basePaths = new HashSet<>();
        for (Swagger swagger : swaggers) {
            if (swagger != null) {
                versions.add(swagger.getSwagger());
                basePaths.add(swagger.getBasePath());
            }
        }

        if (versions.size() > 1) {
            throw new SwaggerException("Version of the swagger docs are not same");
        }

        if (basePaths.size() > 1) {
            throw new SwaggerException("BasePaths of the swagger docs are not same");
        }
    }


}
