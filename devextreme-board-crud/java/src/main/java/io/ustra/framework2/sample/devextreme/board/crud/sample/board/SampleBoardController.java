package io.ustra.framework2.sample.devextreme.board.crud.sample.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample/board")
public class SampleBoardController {

	@Autowired private SampleBoardService sampleBoardService;

	@GetMapping("")
	List<SampleBoardModel> getList(String title) {
		return sampleBoardService.getList(title);
	}

	@GetMapping("/{boardIdx}")
	SampleBoardModel getCodeGroups(@PathVariable Integer boardIdx) {
		return sampleBoardService.get(boardIdx);
	}

	@PostMapping("")
	SampleBoardModel add(@RequestBody SampleBoardModel board) {
		return sampleBoardService.add(board);
	}


}
