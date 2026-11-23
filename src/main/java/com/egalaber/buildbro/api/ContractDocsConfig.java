package com.egalaber.buildbro.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Serves the API contract ({@code src/main/resources/openapi/build-bro-api.yaml}) at
 * {@code /openapi/build-bro-api.yaml}. Swagger UI ({@code static/swagger-ui/index.html}) shows it.
 */
@Configuration
public class ContractDocsConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/openapi/**").addResourceLocations("classpath:/openapi/");
    }
}
