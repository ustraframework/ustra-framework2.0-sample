package io.ustra.framework2.sample.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.gsitm.ustra.java.data.file.aws.AwsS3FileResourceStorage;
import com.gsitm.ustra.java.data.file.config.FileOperationConfigurer;
import com.gsitm.ustra.java.data.file.policy.FileGroupPolicyStore;
import com.gsitm.ustra.java.data.file.processor.AbstractFileOperationProcessor;
import com.gsitm.ustra.java.data.file.processor.extractor.ResourceInfoExtractorResolver;
import com.gsitm.ustra.java.data.file.processor.storage.FileMetaDataStorage;
import com.gsitm.ustra.java.data.file.processor.storage.FileResourceStorage;
import com.gsitm.ustra.java.data.file.processor.storage.LocalFileResourceStorage;
import com.gsitm.ustra.java.management.data.file.FileMetaDataStorageService;
import com.gsitm.ustra.java.management.data.file.UstraManagementFileOperationProcessor;
import com.gsitm.ustra.java.mvc.data.file.WebResourceInfoExtractorResolver;

@EnableConfigurationProperties( { S3MultiProperties.class} )
@Configuration
public class S3MultiFileTestConfiguration {

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE - 2)
	@ConditionalOnExpression("'${ustra.data.file.include-default}' == 'true' and '${ustra.data.file.aws-s3-multi.enabled}' == 'true'")
	public AbstractFileOperationProcessor managementS3MultiFileTestOperationProcessor(FileGroupPolicyStore fileGroupPolicyStore, S3MultiProperties s3MultiProperties) {

		S3MultiFileOperationConfigurer config = new S3MultiFileOperationConfigurer(s3MultiProperties);

		return new UstraManagementFileOperationProcessor(fileGroupPolicyStore , config) {
			@Override
			public boolean supports(String fileGrpId) {
				return "test-multi-s3-upload".contains(fileGrpId);
			}
		};
	}
}
