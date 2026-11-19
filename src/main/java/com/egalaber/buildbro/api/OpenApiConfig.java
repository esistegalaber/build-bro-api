package com.egalaber.buildbro.api;

import com.egalaber.buildbro.api.fault.BuildBroException;
import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.IExceptionInfo;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * OpenAPI document metadata, plus the error responses every operation can return.
 * <p>
 * Error responses are derived from each endpoint method's {@code throws} clause, so documenting a new
 * 404/400 case only means declaring {@link DataNotFoundException} / {@link InvalidRequestException}.
 */
@Configuration
public class OpenApiConfig {

    private static final String EXCEPTION_INFO = IExceptionInfo.class.getSimpleName();

    private static final Map<Class<? extends BuildBroException>, ApiResponse> ERROR_RESPONSES = Map.of(
            DataNotFoundException.class, errorResponse("The requested data does not exist (key `not-found`)."),
            InvalidRequestException.class, errorResponse("The request was invalid (key `invalid-request`).")
    );

    private static final Map<Class<? extends BuildBroException>, String> ERROR_STATUS = Map.of(
            DataNotFoundException.class, "404",
            InvalidRequestException.class, "400"
    );

    @Bean
    OpenAPI buildBroOpenApi(ObjectProvider<BuildProperties> buildProperties) {
        String version = buildProperties.stream().map(BuildProperties::getVersion).findFirst().orElse("dev");
        return new OpenAPI()
                .info(new Info()
                        .title("Build Bro API")
                        .version(version)
                        .description("""
                                Keeps track of what your CI has built and where it has been deployed.

                                Pipelines ask Build Bro for the next build number of a project/branch, register the \
                                resulting build with free-form labels, resolve build sets (named templates such as \
                                an environment) into the concrete builds to ship, and record deployments per server.

                                Errors are returned as `IExceptionInfo`, with HTTP status 404 (`not-found`) or \
                                400 (`invalid-request`).""")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                // relative, so the static docs and Swagger UI work wherever the API is hosted
                .servers(List.of(new Server().url("/").description("This Build Bro instance")));
    }

    /** Registers the {@code IExceptionInfo} schema, which no operation returns on its success path. */
    @Bean
    OpenApiCustomizer exceptionInfoSchema() {
        return openApi -> ModelConverters.getInstance()
                .readAll(IExceptionInfo.class)
                .forEach(openApi.getComponents()::addSchemas);
    }

    /** Adds a 404/400 response to every operation whose method declares the matching exception. */
    @Bean
    OperationCustomizer errorResponsesFromThrowsClause() {
        return (operation, handlerMethod) -> {
            for (Class<?> thrown : handlerMethod.getMethod().getExceptionTypes()) {
                ApiResponse response = ERROR_RESPONSES.get(thrown);
                if (response != null) {
                    if (operation.getResponses() == null) {
                        operation.setResponses(new ApiResponses());
                    }
                    operation.getResponses().putIfAbsent(ERROR_STATUS.get(thrown), response);
                }
            }
            return operation;
        };
    }

    private static ApiResponse errorResponse(String description) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/" + EXCEPTION_INFO))));
    }
}
