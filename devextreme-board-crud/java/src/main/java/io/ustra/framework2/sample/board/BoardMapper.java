package io.ustra.framework2.sample.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gsitm.ustra.java.data.domains.PaginationRequest;

@Mapper
public interface BoardMapper {

	List<BoardDto> select(PaginationRequest paginationRequest, BoardDto.Criteria criteria);
	BoardDto selectDetail(Integer postId);
	int insert(BoardDto board);
	int update(BoardDto board);
	int delete(Integer postId);
}
