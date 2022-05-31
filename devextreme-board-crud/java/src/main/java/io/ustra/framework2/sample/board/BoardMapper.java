package io.ustra.framework2.sample.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gsitm.ustra.java.data.domains.PaginationRequest;

@Mapper
public interface BoardMapper {

	List<BoardModel> select(PaginationRequest paginationRequest, BoardModel.Criteria criteria);
	BoardModel selectDetail(Integer postId);
	int insert(BoardModel board);
	int update(BoardModel board);
	int delete(Integer postId);
}
