package io.ustra.framework2.sample.devextreme.board.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import com.gsitm.ustra.java.security.config.UstraSecurityConfigure;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
			          .apis(RequestHandlerSelectors.basePackage("sample.ustraframework.java"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(swaggerInfo());
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("U.STRA Framework System Swagger")
                .description("U.STRA Framework System API 문서")
                //.license("HDHS Help System")
                //.licenseUrl("http://www.swaggerSample.com")
                .version("1.0.0")
                .build();
    }

    @Bean
    UstraSecurityConfigure swaggerSecurityConfigure() {
    	return new UstraSecurityConfigure() {
    		@Override
    		public void postConfigure(WebSecurity web) throws Exception {
    			web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**");
    		}
    	};
    }
}
