package io.ustra.framework2.sample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(ignoreUnknownFields = true, prefix = "ustra.data.file")
public class S3MultiProperties {
	/**
	 * AWS S3 설정
	 */
	private AwsS3Multi awsS3Multi;


	@Data
	public static class AwsS3Multi {
		/**
		 * AWS S3 저장소를 사용할 것인지 여부
		 */
		private boolean enabled;

		/**
		 * 인증 access-key
		 */
		private String accessKey;

		/**
		 * 인증 secret-key
		 */
		private String secretKey;

		/**
		 * AWS S3 버킷 명
		 */
		private String bucketName;

		/**
		 * AWS region 명
		 */
		private String regionName;
	}
}
