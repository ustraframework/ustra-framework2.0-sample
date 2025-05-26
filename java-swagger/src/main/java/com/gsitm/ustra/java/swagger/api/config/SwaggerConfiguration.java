package com.gsitm.ustra.java.swagger.api.config;

import com.gsitm.ustra.java.core.utils.ProfileSupport;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!" + ProfileSupport.PRODUCTION_PROFILE)
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("U.STRA Framework Code Swagger Sample")
                        .description("U.STRA Framework Sample API Document")
                        .version("0.0.1"));
    }
}