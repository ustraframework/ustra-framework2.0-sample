package io.ustra.framework2.sample;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gsitm.ustra.java.mvc.app.ServletApplicationRunner;

@SpringBootApplication
public class AwsS3SampleApplication extends ServletApplicationRunner{

	public static void main(String[] args) throws IOException {
		ServletApplicationRunner.run(AwsS3SampleApplication.class, args);
	}

}
