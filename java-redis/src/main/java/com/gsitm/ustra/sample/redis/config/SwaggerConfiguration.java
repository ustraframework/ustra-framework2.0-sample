package com.gsitm.ustra.sample.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.gsitm.ustra.java.core.utils.ProfileSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile("!" + ProfileSupport.PRODUCTION_PROFILE)
@EnableSwagger2
public class SwaggerConfiguration {

	private ApiInfo swaggerInfo() {

		return new ApiInfoBuilder()
					.title("U.STRA Framework Sample Swagger")
					.description("U.STRA Framework Sample API Document")
					.version("0.0.1")
					.build();

	}

	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.gsitm.ustra.java.sample"))
				.paths(PathSelectors.ant("/api/**"))
				.build()
				.apiInfo(swaggerInfo());
	}



}
