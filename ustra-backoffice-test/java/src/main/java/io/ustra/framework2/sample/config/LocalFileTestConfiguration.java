package io.ustra.framework2.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.gsitm.ustra.java.data.file.policy.FileGroupPolicyStore;
import com.gsitm.ustra.java.data.file.processor.AbstractFileOperationProcessor;
import com.gsitm.ustra.java.management.data.file.UstraManagementFileOperationProcessor;

@Configuration
public class LocalFileTestConfiguration {

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE - 2)
	public AbstractFileOperationProcessor managementLocalFileTestOperationProcessor(FileGroupPolicyStore fileGroupPolicyStore) {
		return new UstraManagementFileOperationProcessor(fileGroupPolicyStore) {
			@Override
			public boolean supports(String fileGrpId) {
				return "test-local-upload".contains(fileGrpId);
			}
		};
	}
}
