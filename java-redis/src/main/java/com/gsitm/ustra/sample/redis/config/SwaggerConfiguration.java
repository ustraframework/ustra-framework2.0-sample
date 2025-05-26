package com.gsitm.ustra.sample.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.gsitm.ustra.java.core.utils.ProfileSupport;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@Profile("!" + ProfileSupport.PRODUCTION_PROFILE)
public class SwaggerConfiguration {

	@Bean
	public OpenAPI swaggerInfo() {
		return new OpenAPI()
				.info(new Info()
						.title("U.STRA Framework Sample Swagger")
						.description("U.STRA Framework Sample API Document")
						.version("0.0.1"));
	}
}
