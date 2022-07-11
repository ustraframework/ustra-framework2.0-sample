package io.ustra.framework2.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.gsitm.ustra.java.data.file.config.FileOperationConfigurer;
import com.gsitm.ustra.java.data.file.policy.FileGroupPolicyStore;
import com.gsitm.ustra.java.data.file.processor.AbstractFileOperationProcessor;
import com.gsitm.ustra.java.data.file.processor.storage.FileResourceStorage;
import com.gsitm.ustra.java.data.file.processor.storage.LocalFileResourceStorage;
import com.gsitm.ustra.java.management.data.file.UstraManagementFileOperationProcessor;

@Configuration
public class LocalFileTestConfiguration {

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE - 2)
	public AbstractFileOperationProcessor managementLocalFileTestOperationProcessor(FileGroupPolicyStore fileGroupPolicyStore) {

//		FileOperationConfigurer config = new FileOperationConfigurer() {
//
//			@Override
//			public FileResourceStorage fileResourceStorage() {
//				return new XXXFileResourceStorage();
//			}
//
//		};

		return new UstraManagementFileOperationProcessor(fileGroupPolicyStore /*, config */) {
			@Override
			public boolean supports(String fileGrpId) {
				return "test-local-upload".contains(fileGrpId);
			}
		};
	}
}
