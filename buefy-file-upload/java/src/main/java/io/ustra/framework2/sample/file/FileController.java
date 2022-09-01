package io.ustra.framework2.sample.file;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gsitm.ustra.java.data.file.FileOperationManager;
import com.gsitm.ustra.java.data.file.model.FileConvertInput;
import com.gsitm.ustra.java.data.file.model.FileMetaData;
import com.gsitm.ustra.java.data.file.model.FileMetaDataInput;
import com.gsitm.ustra.java.data.file.model.FileUploadInput;
import com.gsitm.ustra.java.data.file.model.FileUploadOutput;
import com.gsitm.ustra.java.data.file.policy.FileGroupPolicy;
import com.gsitm.ustra.java.data.file.policy.FileGroupPolicyStore;
import com.gsitm.ustra.java.mvc.data.file.WebResourceAttachFileConverter;
import com.gsitm.ustra.java.mvc.utils.UstraMultipartFileResource;

@Controller
@RequestMapping("/api/file")
public class FileController {

	@Autowired private FileOperationManager fileOperationManager;
	@Autowired private FileGroupPolicyStore fileGroupPolicyStore;

	@GetMapping("/groups")
	@ResponseBody
	Collection<FileGroupPolicy> groups() {
		return this.fileGroupPolicyStore.getAll().values();
	}

	@GetMapping("")
	@ResponseBody
	Collection<FileMetaData> files(@RequestParam String fileGrpId, @RequestParam String fileId) {
		return fileOperationManager.getMetaDatas(
				FileMetaDataInput.builder().
					fileGrpId(fileGrpId).
					fileId(fileId).
					build()
		);
	}


	@PostMapping("")
	@ResponseBody
	String upload(MultipartHttpServletRequest request) {

		FileUploadInput.UploadFile uploadFiles = new FileUploadInput.UploadFile();
		List<Resource> resources = new LinkedList<>();
		List<Integer> fileNos = new LinkedList<>();

		int i = 0;
		while(true) {
			i++;
			if(request.getMultiFileMap().get("file-" + (i)) != null) {
				List<MultipartFile> files = request.getMultiFileMap().get("file-" + (i));
				for (MultipartFile file : files) {
					resources.add(new UstraMultipartFileResource(file));
				}
			}

			if(StringUtils.isNotEmpty(request.getParameter("file-" + (i)))) {
				Integer number = Integer.parseInt(request.getParameter("file-" + (i)));
				fileNos.add(number);
			}

			if(request.getMultiFileMap().get("file-" + (i + 1)) == null && StringUtils.isEmpty(request.getParameter("file-" + (i + 1)))) {
				break;
			}

		}

		uploadFiles.setResources(resources);
		uploadFiles.setFileNos(fileNos);


		FileUploadInput input = FileUploadInput.builder()
				.fileGrpId(request.getParameter("fileGrpId"))
				.fileId(request.getParameter("fileId"))
 				.files(FileUploadInput.UploadFile.ofResourceAndMetaData(fileNos, resources))
				.build();


		FileUploadOutput output = fileOperationManager.upload(input);

		return output.getFileId();

	}

	@GetMapping("/attach")
	public ResponseEntity<?> attachConvert(
			@RequestParam String fileId,
			@RequestParam Integer fileNo,
			@RequestParam String fileGrpId,
			@RequestParam String attachmentFileName,
			@RequestParam Integer attach,
			HttpServletRequest request,
			HttpServletResponse response) {

		return fileOperationManager.convert(
				WebResourceAttachFileConverter.builder(fileGrpId, attachmentFileName, attach > 0, request, response)
				.metaDataId(FileConvertInput.FileMetaId.builder().fileId(fileId).fileNos(fileNo == null ? null :Arrays.asList(fileNo)).build())
				.build());

	}

}
