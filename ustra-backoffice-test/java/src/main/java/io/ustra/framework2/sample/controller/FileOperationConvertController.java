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

	private String fileId = "xYTN3ATOycTNwATM04ENiFnVSFUTKFTVzRzTxpWekTO8CaI"; // 같은 파일아이디 1개 (파일 1개 단독 다운로드)
//	private String fileId = "xYTN3ATOyYDM0kzM500SQdjUDVTaKp0auZmblZlYodmdR5E"; // 같은 파일아이디 2개 (파일 2개 zip 다운로드)

	@Autowired private FileOperationManager fileOperationManager;

	// Resource From AWS
	@RequestMapping("/api/test/convert")
	public ResponseEntity<?> fileDownload(HttpServletRequest request, HttpServletResponse response) {

		return fileOperationManager.convert(
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
