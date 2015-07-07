package com.wavemaker.tools.apidocs.tools.plugin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wavemaker.tools.apidocs.tools.core.model.Scheme;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;
import com.wavemaker.tools.apidocs.tools.core.utils.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.parser.config.CollectionFormat;
import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.exception.SwaggerParserException;
import com.wavemaker.tools.apidocs.tools.parser.resolver.ParameterResolver;
import com.wavemaker.tools.apidocs.tools.parser.runner.SwaggerParser;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableClassScanner;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableModelScanner;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableScanner;
import com.wavemaker.tools.apidocs.tools.spring.SpringSwaggerParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 8/6/15
 */
@Mojo(defaultPhase = LifecyclePhase.PREPARE_PACKAGE, name = "document", configurator =
        "include-project-dependencies", requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class SwaggerMojo extends AbstractMojo {

    @Parameter(required = true)
    private String baseUrl;

    @Parameter
    private List<String> schemes;

    @Parameter
    private String collectionFormat;

    @Parameter
    private ApiInfo info;

    @Parameter(name = "parameter-resolvers")
    private Map<String, String> parameterResolvers;

    @Parameter(name = "class-scanner")
    private ScannerConfiguration classScanner;

    @Parameter(name = "model-scanner")
    private ScannerConfiguration modelScanner;

    @Parameter(required = true)
    private String fileName;

    @Parameter(defaultValue = "${project.build.directory}/apidocs")
    private String path;


    private ObjectMapper mapper;

    public SwaggerMojo() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    private SwaggerConfiguration buildConfiguration() throws MojoFailureException {
        FilterableClassScanner classScanner = buildScanner(this.classScanner, new FilterableClassScanner());
        FilterableModelScanner modelScanner = buildScanner(this.modelScanner, new FilterableModelScanner());
        SwaggerConfiguration.Builder builder = new SwaggerConfiguration.Builder(baseUrl, classScanner);
        builder.setModelScanner(modelScanner);

        buildParameterResolvers(builder);

        if (StringUtils.isNotBlank(collectionFormat)) {
            builder.setCollectionFormat(CollectionFormat.forValue(collectionFormat));
        }
        builder.setInfo(info.build());

        if (CollectionUtil.isNotEmpty(schemes)) {
            List<Scheme> schemeList = new LinkedList<>();
            for (final String scheme : schemes) {
                schemeList.add(Scheme.forValue(scheme));
            }
            builder.setSchemes(schemeList);
        }

        builder.setClassLoader(Thread.currentThread().getContextClassLoader());

        return builder.build();
    }

    private void buildParameterResolvers(final SwaggerConfiguration.Builder builder) throws MojoFailureException {

        if (parameterResolvers != null) {
            for (final Map.Entry<String, String> entry : parameterResolvers.entrySet()) {
                Class<?> resolvableType;
                ParameterResolver resolver;
                Class<?> resolverType;
                try {
                    resolvableType = Class.forName(entry.getKey());
                } catch (ClassNotFoundException e) {
                    throw new MojoFailureException("Resolvable class not found: " + entry.getKey(), e);
                }
                try {
                    resolverType = Class.forName(entry.getValue(), true, this.getClass().getClassLoader());
                } catch (ClassNotFoundException e) {
                    throw new MojoFailureException("Resolver class not found: " + entry.getValue(), e);
                }
                try {
                    resolver = (ParameterResolver) resolverType.newInstance();
                } catch (InstantiationException e) {
                    throw new MojoFailureException("Error while creating new instance of parameter resolver:" + entry
                            .getValue(), e);
                } catch (IllegalAccessException e) {
                    throw new MojoFailureException("Error while instatiation of parameter resolver class:" + entry
                            .getValue(), e);
                }
                builder.addParameterResolver(resolvableType, resolver);
            }
        }

    }

    private <T extends FilterableScanner> T buildScanner(ScannerConfiguration configuration, T scanner) {

        if (configuration == null) {
            scanner.includeAll();
        } else {
            scanner.includePackages(configuration.getIncludePackages());
            scanner.excludePackages(configuration.getExcludePackages());

            scanner.includeTypes(configuration.getIncludeClasses());
            scanner.excludeTypes(configuration.getExcludeClasses());
        }

        return scanner;
    }


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        SwaggerParser swaggerParser = new SpringSwaggerParser(buildConfiguration());
        Swagger swagger;
        try {
            swagger = swaggerParser.generate();
        } catch (SwaggerParserException e) {
            throw new MojoFailureException("Error while parsing classes", e);
        }

        File outputDirectory = new File(path);
        outputDirectory.mkdirs();
        try {
            mapper.writeValue(new File(outputDirectory, fileName), swagger);
        } catch (IOException e) {
            throw new MojoFailureException("Error while creating file", e);
        }
    }
}
