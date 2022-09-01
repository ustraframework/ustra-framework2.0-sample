package io.ustra.framework2.sample.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gsitm.ustra.java.core.utils.UstraSystemUtils;
import com.gsitm.ustra.java.data.file.model.FileMetaData;
import com.gsitm.ustra.java.data.file.processor.storage.FileMetaDataStorage;

/**
 * 데이터베이스에 파일 정보를 저장하는 FileMetaDataStorage
 * @author RoyLee
 *
 */
@Transactional
public class JdbcFileMetaDataStorage implements FileMetaDataStorage {

	@Autowired private FileMapper fileMapper;

	@Override
	public List<FileMetaData> getMetaDatas(String fileGrpId, String fileId, List<Integer> fileNos) {
		return fileMapper.select(fileId, fileNos);
	}

	@Override
	public void addMetaData(String fileGrpId, FileMetaData metaData) {
		fileMapper.insert(metaData);
	}

	@Override
	public void delMetaData(String fileGrpId, String fileId, Integer fileNo) {
		fileMapper.delete(fileId, fileNo);
	}

	@Override
	public void delAllMetaData(String fileGrpId, String fileId) {
		fileMapper.delete(fileId, null);
	}

	@Override
	public String getNewFileId(String fileGrpId, String additionalKey) {
		return UstraSystemUtils.processBase62UniqueId();
	}

}
