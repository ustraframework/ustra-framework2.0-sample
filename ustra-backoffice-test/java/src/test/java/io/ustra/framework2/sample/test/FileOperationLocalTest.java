package io.ustra.framework2.sample.test;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.gsitm.ustra.java.data.file.FileOperationManager;
import com.gsitm.ustra.java.data.file.model.FileUploadInput;
import com.gsitm.ustra.java.data.file.model.FileUploadInput.UploadFile;
import com.gsitm.ustra.java.data.file.model.FileUploadOutput;
import com.gsitm.ustra.java.test.UstraSpringMockMvcTest;
import io.ustra.framework2.sample.AwsS3SampleApplication;

@EnableAutoConfiguration
@SpringBootTest(classes = AwsS3SampleApplication.class)
public class FileOperationLocalTest extends UstraSpringMockMvcTest {

	private String uploadedFileId = null;

	private static final String UPLOAD_TARGET_FILE1 = "/upload/source/test_dog.jpg";
	private static final String UPLOAD_TARGET_FILE2 = "/upload/source/test_cat.jpg";

	private static final String FILE_GROUP_ID_LOCAL = "test-local-upload"; // path : upload/test/local

	@Autowired private FileOperationManager fileOperationManager;

	@Test
	public void upload() {

		FileUploadInput input = FileUploadInput.builder()
			.fileGrpId(FILE_GROUP_ID_LOCAL)
			.files(UploadFile.fromResource(new FileSystemResource(UPLOAD_TARGET_FILE1)))
			.build();

		FileUploadOutput output = this.fileOperationManager.upload(input);
		this.uploadedFileId = output.getFileId();

		Assertions.assertNotNull(this.uploadedFileId);
		Assertions.assertTrue(output.getFileMetaDatas().size() == 1);


	}

	@Test
	public void editToAddResource() {
		FileUploadInput input = FileUploadInput.builder()
				.fileGrpId(FILE_GROUP_ID_LOCAL)
				.fileId(this.uploadedFileId)
				.files(UploadFile.ofResourceAndMetaData(Arrays.asList(1), new FileSystemResource(UPLOAD_TARGET_FILE2)))
				.build();

		FileUploadOutput output = this.fileOperationManager.upload(input);
		this.uploadedFileId = output.getFileId();

		Assertions.assertNotNull(this.uploadedFileId);
		Assertions.assertTrue(output.getFileMetaDatas().size() == 2);
	}

//	@Test
//	public void editToRemoveResource() {
//		FileUploadInput input = FileUploadInput.builder()
//				.fileGrpId(FILE_GROUP_ID_LOCAL)
//				.fileId(this.uploadedFileId)
//				.files(UploadFile.fromMetaData(Arrays.asList(2)))
//				.build();
//
//		FileUploadOutput output = this.fileOperationManager.upload(input);
//		this.uploadedFileId = output.getFileId();
//
//		Assertions.assertNotNull(this.uploadedFileId);
//		Assertions.assertTrue(output.getFileMetaDatas().size() == 1);
//	}

	@Test
	public void downloadFile() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/test/convert/local"))
		.andExpect(MockMvcResultMatchers.status().isOk());

	}


}
