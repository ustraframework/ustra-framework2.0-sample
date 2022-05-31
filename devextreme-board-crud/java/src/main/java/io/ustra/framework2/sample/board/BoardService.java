package io.ustra.framework2.sample.board;

import java.util.List;

import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import com.gsitm.ustra.java.data.domains.PaginationRequest;
import com.gsitm.ustra.java.data.exception.UstraDataResponseCode;
import com.gsitm.ustra.java.data.validation.CrudGroups;

@Service
@Transactional
@Validated
public class BoardService {

	@Autowired private BoardMapper sampleBoardMapper;

	/**
	 * 게시 목록 조회
	 * @param paginationRequest 페이징 정보
	 * @param criteria 검색 조건
	 * @return 게시 목록
	 */
	public List<BoardModel> getBoards(PaginationRequest paginationRequest, BoardModel.Criteria criteria) {
		return sampleBoardMapper.select(paginationRequest, criteria);
	}

	/**
	 * 게시 아이디 조회
	 * @param postId 게시 아이디
	 * @return 게시 상세 정보
	 */
	public BoardModel get(Integer postId) {
		return sampleBoardMapper.selectDetail(postId);
	}

	/**
	 * 추가
	 * @param board 게시 정보
	 * @return 등록 정보
	 */
	public BoardModel add(@ConvertGroup(to=CrudGroups.Add.class) @Valid BoardModel board) {
		int result = sampleBoardMapper.insert(board);

		if (result < 1) {
			throw UstraDataResponseCode.CANNOT_SAVE_RECORD.exception();
		}

		return board;
	}

	/**
	 * 수정
	 * @param board 게시 정보
	 * @return 수정 정보
	 */
	public BoardModel edit(@ConvertGroup(to=CrudGroups.Edit.class) @Valid BoardModel board) {
		int result = sampleBoardMapper.update(board);

		if (result < 1) {
			throw UstraDataResponseCode.CANNOT_SAVE_RECORD.exception();
		}

		return board;
	}

	/**
	 * 삭제
	 * @param postId 게시 아이디
	 * @return 삭제 건수
	 */
	public int remove(Integer postId) {

		Assert.notNull(postId, "게시 아이디는 필수 값입니다.");

		return sampleBoardMapper.delete(postId);
	}

}
