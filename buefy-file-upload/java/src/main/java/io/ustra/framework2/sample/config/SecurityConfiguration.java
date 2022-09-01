package io.ustra.framework2.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.gsitm.ustra.java.security.config.UstraSecurityConfigure;

@Configuration
public class SecurityConfiguration {
	@Bean
    UstraSecurityConfigure h2SecurityConfigure() {
    	return new UstraSecurityConfigure() {
    		@Override
    		public void postConfigure(WebSecurity web) throws Exception {
    			web.ignoring().antMatchers("/h2-console/**");
    		}

    		@Override
    		public void postConfigure(HttpSecurity http) throws Exception {
    			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    		}
    	};
    }
}
