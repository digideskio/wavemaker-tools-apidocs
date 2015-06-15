package com.wavemaker.tools.apidocs.tools.plugin;

import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import com.wavemaker.tools.apidocs.tools.core.model.Info;
import com.wavemaker.tools.apidocs.tools.core.model.Scheme;
import com.wavemaker.tools.apidocs.tools.core.utils.CollectionUtil;
import com.wavemaker.tools.apidocs.tools.parser.config.CollectionFormat;
import com.wavemaker.tools.apidocs.tools.parser.config.SwaggerConfiguration;
import com.wavemaker.tools.apidocs.tools.parser.exception.SwaggerParserException;
import com.wavemaker.tools.apidocs.tools.parser.runner.SwaggerParser;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableClassScanner;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableModelScanner;
import com.wavemaker.tools.apidocs.tools.parser.scanner.FilterableScanner;
import com.wavemaker.tools.apidocs.tools.spring.SpringSwaggerParser;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 8/6/15
 */
@Mojo(defaultPhase = LifecyclePhase.INSTALL, name = "document", configurator =
        "include-project-dependencies", requiresDependencyResolution = ResolutionScope.RUNTIME)
public class SwaggerMojo extends AbstractMojo {

    @Parameter(required = true)
    private String baseUrl;

    @Parameter
    private List<Scheme> schemes;

    @Parameter(name = "collection-format")
    private CollectionFormat collectionFormat;

    @Parameter
    private Info info;

    @Parameter(name = "parameter-resolvers")
    private Map<String, String> parameterResolvers;

    @Parameter(name = "class-scanner")
    private ScannerConfiguration classScannerConfiguration;

    @Parameter(name = "model-scanner")
    private ScannerConfiguration modelScannerConfiguration;


    private SwaggerConfiguration buildConfiguration() {
        FilterableClassScanner classScanner = buildScanner(classScannerConfiguration, new FilterableClassScanner());
        FilterableModelScanner modelScanner = buildScanner(modelScannerConfiguration, new FilterableModelScanner());
        SwaggerConfiguration.Builder builder = new SwaggerConfiguration.Builder(baseUrl, classScanner);
        builder.setModelScanner(modelScanner);

        builder.setCollectionFormat(collectionFormat);
        builder.setInfo(info);

        if (CollectionUtil.isNotEmpty(schemes)) {
            builder.setSchemes(schemes);
        }

        return builder.build();
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
        try {
            swaggerParser.generate();
        } catch (SwaggerParserException e) {
            throw new MojoFailureException("Error while generating Swagger", e);
        }

    }
}
