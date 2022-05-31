package io.ustra.framework2.sample.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsitm.ustra.java.mvc.rest.utils.UstraRestUtils;

@RestController
@RequestMapping("/api/sample/board")
public class BoardController {

	@Autowired private BoardService boardService;

	@GetMapping("")
	List<BoardModel> getBoards(String keyword) {
		return boardService.getBoards(UstraRestUtils.getCurrentApiHeader(),
				BoardModel.Criteria
					.builder()
					.keyword(keyword)
					.build()
				);
	}

	@GetMapping("/{postId}")
	BoardModel getBoard(@PathVariable Integer postId) {
		return boardService.get(postId);
	}

	@PostMapping("")
	BoardModel add(@RequestBody BoardModel board) {
		return boardService.add(board);
	}

	@PutMapping("")
	BoardModel edit(@RequestBody BoardModel board) {
		return boardService.edit(board);
	}

	@DeleteMapping("/{postId}")
	void remove(@PathVariable Integer postId) {
		boardService.remove(postId);
	}
}
