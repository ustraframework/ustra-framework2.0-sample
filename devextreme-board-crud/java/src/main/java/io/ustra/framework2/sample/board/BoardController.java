package io.ustra.framework2.sample.board;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gsitm.ustra.java.mvc.rest.utils.UstraRestUtils;
import com.gsitm.ustra.java.security.authority.permission.Permission;

import io.ustra.framework2.sample.common.ProgramIds;

@RestController
@RequestMapping("/api/sample/board")
public class BoardController {

	@Autowired private BoardService boardService;

	@GetMapping("")
	@Permission
	List<BoardModel> getBoards(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime srtDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
		return boardService.getBoards(UstraRestUtils.getCurrentApiHeader(),
				BoardModel.Criteria
					.builder()
					.keyword(keyword)
					.srtDate(srtDate)
					.endDate(endDate)
					.build()
				);
	}

	@GetMapping("/{postId}")
	@Permission
	BoardModel getBoard(@PathVariable Integer postId) {
		return boardService.get(postId);
	}

	@PostMapping("")
	@Permission(roles = { ProgramIds.SAMPLE_BOARD })
	BoardModel add(@RequestBody BoardModel board) {
		return boardService.add(board);
	}

	@PutMapping("")
	@Permission(roles = { ProgramIds.SAMPLE_BOARD })
	BoardModel edit(@RequestBody BoardModel board) {
		return boardService.edit(board);
	}

	@DeleteMapping("/{postId}")
	@Permission(roles = { ProgramIds.SAMPLE_BOARD })
	void remove(@PathVariable Integer postId) {
		boardService.remove(postId);
	}
}
