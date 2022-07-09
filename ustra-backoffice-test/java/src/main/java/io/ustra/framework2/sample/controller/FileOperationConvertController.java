package io.ustra.framework2.sample.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gsitm.ustra.java.data.file.FileOperationManager;
import com.gsitm.ustra.java.data.file.model.FileConvertInput;
import com.gsitm.ustra.java.mvc.data.file.WebResourceAttachFileConverter;

@RestController
public class FileOperationConvertController {

	@Autowired private FileOperationManager fileOperationManager;


	private String fileId = "";	// 업로드 된 파일 아이디 작성

	// Resource From AWS
	@RequestMapping("/api/test/convert/s3")
	public ResponseEntity<?> fileDownload(HttpServletRequest request, HttpServletResponse response) {

		return fileOperationManager.convert(
				// 파일이 하나일 경우 convertTestFile 라는 파일명으로 다운로드 된다.
				WebResourceAttachFileConverter.builder("test-s3-upload", "convertTestFile", true, request, response)
				.metaDataId(FileConvertInput.FileMetaId.builder().fileId(fileId).build())
				.build());
	}



	// Resource From Local
	@RequestMapping("/api/test/convert/local")
	public ResponseEntity<?> fileDownloadForLocalFile(HttpServletRequest request, HttpServletResponse response) {

		return fileOperationManager.convert(
				WebResourceAttachFileConverter.builder("test-local-upload","convertLocalFileTest", true, request, response)
				.resources(Arrays.asList(
						new FileSystemResource("/upload/source/excel-pagination-test.xlsx"),
						new FileSystemResource("/upload/source/excel-pagination-test2.xlsx")
						))
				.build());
	}
}
