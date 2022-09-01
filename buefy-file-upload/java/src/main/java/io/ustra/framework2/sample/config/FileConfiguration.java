package io.ustra.framework2.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gsitm.ustra.java.data.file.config.FileOperationConfigurer;
import com.gsitm.ustra.java.data.file.policy.FileGroupPolicyStore;
import com.gsitm.ustra.java.data.file.processor.AbstractFileOperationProcessor;
import com.gsitm.ustra.java.data.file.processor.extractor.ResourceInfoExtractorResolver;
import com.gsitm.ustra.java.data.file.processor.storage.FileMetaDataStorage;
import com.gsitm.ustra.java.mvc.data.file.WebResourceInfoExtractorResolver;

import io.ustra.framework2.sample.file.JdbcFileMetaDataStorage;

@Configuration
public class FileConfiguration {

	@Bean
	FileMetaDataStorage jdbcFileMetaDataStorage() {
		return new JdbcFileMetaDataStorage();
	}

	@Bean
	AbstractFileOperationProcessor defaultFileOperationProcessor(FileGroupPolicyStore fileGroupPolicyStore) {

		return new AbstractFileOperationProcessor(fileGroupPolicyStore, new FileOperationConfigurer() {

			@Override
			public ResourceInfoExtractorResolver resourceInfoExtractorResolver() {
				return new WebResourceInfoExtractorResolver();
			}

			@Override
			public FileMetaDataStorage fileMetaDataStorage() {
				return jdbcFileMetaDataStorage();
			}

		}) {

			@Override
			public boolean supports(String fileGrpId) {
				return true;
			}
		};
	}
}
