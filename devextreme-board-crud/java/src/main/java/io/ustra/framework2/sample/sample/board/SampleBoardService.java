package io.ustra.framework2.sample.sample.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsitm.ustra.java.data.exception.UstraDataResponseCode;

@Service
@Transactional
public class SampleBoardService {

	@Autowired private SampleBoardMapper sampleBoardMapper;

	public List<SampleBoardModel> getList(String title) {
		return sampleBoardMapper.select(title);
	}

	public SampleBoardModel get(Integer boardIdx) {
		return sampleBoardMapper.selectDetail(boardIdx);
	}

	public SampleBoardModel add(SampleBoardModel board) {
		int result = sampleBoardMapper.insert(board);

		if (result < 1) {
			throw UstraDataResponseCode.CANNOT_SAVE_RECORD.exception();
		}

		return board;
	}

}
