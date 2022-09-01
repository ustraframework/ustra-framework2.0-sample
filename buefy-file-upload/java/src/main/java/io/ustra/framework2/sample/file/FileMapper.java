package io.ustra.framework2.sample.file;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gsitm.ustra.java.data.file.model.FileMetaData;

@Mapper
public interface FileMapper {
	List<FileMetaData> select(@Param("fileId") String fileId, @Param("fileNos") List<Integer> fileNos);
	int insert(FileMetaData meta);
	int delete(@Param("fileId") String fileId, @Param("fileNo") Integer fileNo);
}
