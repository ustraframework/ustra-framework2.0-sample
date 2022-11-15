package io.ustra.framework2.sample.config;

import io.ustra.framework2.sample.config.S3MultiProperties.AwsS3Multi;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.core.io.s3.SimpleStorageProtocolResolver;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gsitm.ustra.java.data.file.model.FileMetaData;
import com.gsitm.ustra.java.data.file.model.FileRemoveInput;
import com.gsitm.ustra.java.data.file.policy.FileGroupPolicy;
import com.gsitm.ustra.java.data.file.processor.storage.FileResourceStorage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AwsS3MultiFileResourceStorage implements FileResourceStorage {

	@Getter
	private S3MultiProperties.AwsS3Multi awsProperties;

	@Getter
	private AmazonS3 s3Client;

	@Getter
	private ProtocolResolver storageProtocolResolver;

	@Autowired
	private ResourceLoader resourceLoader;

	public AwsS3MultiFileResourceStorage(S3MultiProperties.AwsS3Multi properties) {
		this.awsProperties = properties;
		this.s3Client = this.createClient();
		this.storageProtocolResolver = this.createStorageProtocolResolver();

		log.info("AwsS3FileResourceStorage is activated.... \r\n : {}", properties);
	}

	/**
	 * AmazonS3 객체 생성
	 *	프로퍼티에 인증정보가 포함될 경우 BasicAWSCredentials 객체를 사용하여 인증 처리
	 *  그 외는 DefaultAWSCredentialsProviderChain 을 사용하여 설정 됨.
	 * @see DefaultAWSCredentialsProviderChain
	 * @see BasicAWSCredentials
	 * @return
	 */
	protected AmazonS3 createClient () {

		// AWS 인증 정보 생성
		AWSCredentials credentials;
		if (StringUtils.isEmpty(this.awsProperties.getAccessKey())) {
			credentials = DefaultAWSCredentialsProviderChain.getInstance().getCredentials();
		} else {
			credentials = new BasicAWSCredentials(
	        		this.awsProperties.getAccessKey()
	        		, this.awsProperties.getSecretKey()
	        		);
		}


        // AWS 인증 정보를 포함하여 AmazonS3 객체 생성
        AmazonS3 amazonS3 = AmazonS3ClientBuilder
        		.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.awsProperties.getRegionName())
                .build();

		return amazonS3;
	}

	/**
	 * ProtocolResolver 객체 생성
	 * 	ProtocolResolver : resource URL을 Resource 객체로 변환
	 * @see SimpleStorageProtocolResolver
	 * @return
	 */
	protected ProtocolResolver createStorageProtocolResolver() {
		return new SimpleStorageProtocolResolver(this.s3Client);
	}

	@Override
	public String addFileStorage(FileGroupPolicy policy, List<FileMetaData> details, String targetPath) {

        String bucketName = this.awsProperties.getBucketName();
        String key = "";

        for(FileMetaData detail : details) {
        	key = targetPath + detail.getFileName();
        	// File 객체가 아닌 stream을 통한 업로드를 위하여 파일 메타 정보 생성
        	ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(detail.getFileExt());
            // AmazonS3 객체를 통한 S3 파일 연계
            try {
            	this.s3Client.putObject(new PutObjectRequest(bucketName, key, detail.getResource().getInputStream(), objectMetadata));
			} catch (IOException e) {
			}

        }

        // 업로드 이후 url 정보 및 key 값 리턴
        return this.s3Client.getUrl(bucketName, key).toString();
	}

	@Override
	public void delFileStorage(FileRemoveInput removeInput) {
		String bucketName = this.awsProperties.getBucketName();
		List<String> keys =  removeInput.getFiles().getPaths();	// targetPath + detail.getFileName();

        for(String key : keys) {
        	this.s3Client.deleteObject(bucketName, key);
        }
	}

	@Override
	public Resource getFileResource(FileMetaData metaData) {
		String key = metaData.getFilePath() + metaData.getFileName();
		String bucketName = this.awsProperties.getBucketName();
		String s3uri = "s3://" + bucketName + "/" + key;

		return this.storageProtocolResolver.resolve(s3uri, resourceLoader);

	}

	@Override
	public Resource getResourceByPath(String path) {

		return new FileSystemResource(path);
	}

}