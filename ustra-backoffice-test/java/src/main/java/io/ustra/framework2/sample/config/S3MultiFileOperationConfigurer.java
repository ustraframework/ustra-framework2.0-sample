package io.ustra.framework2.sample.config;

import com.gsitm.ustra.java.data.file.aws.AwsS3FileResourceStorage;
import com.gsitm.ustra.java.data.file.config.FileOperationConfigurer;
import com.gsitm.ustra.java.data.file.processor.extractor.ResourceInfoExtractorResolver;
import com.gsitm.ustra.java.data.file.processor.storage.FileMetaDataStorage;
import com.gsitm.ustra.java.data.file.processor.storage.FileResourceStorage;
import com.gsitm.ustra.java.management.data.file.FileMetaDataStorageService;
import com.gsitm.ustra.java.mvc.data.file.WebResourceInfoExtractorResolver;

public class S3MultiFileOperationConfigurer implements FileOperationConfigurer{


	private S3MultiProperties s3MultiProperties;

	public S3MultiFileOperationConfigurer(S3MultiProperties properties){
		this.s3MultiProperties = properties;
	}

	/**
	 * 파일 저장소 저장
	 */
	@Override
	public FileResourceStorage fileResourceStorage() {
		return new AwsS3MultiFileResourceStorage(this.s3MultiProperties.getAwsS3Multi());
	}

	/**
	 * Web Upload
	 */
	@Override
	 public ResourceInfoExtractorResolver resourceInfoExtractorResolver() {
		return new WebResourceInfoExtractorResolver() {};
	}

	@Override
	public FileMetaDataStorage fileMetaDataStorage() {
		return new FileMetaDataStorageService();
	}

}
