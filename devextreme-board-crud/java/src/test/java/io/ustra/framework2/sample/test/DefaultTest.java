package io.ustra.framework2.sample.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.gsitm.ustra.java.test.UstraSpringTest;

import io.ustra.framework2.sample.board.BoardDto;
import io.ustra.framework2.sample.board.BoardMapper;
import lombok.extern.slf4j.Slf4j;

@EnableAutoConfiguration
@ComponentScan("io.ustra.framework2.sample")
@Slf4j
public class DefaultTest extends UstraSpringTest {

	@Autowired private BoardMapper boardMapper;

	@Test
	public void getDetail() {
		BoardDto boardInfo = this.boardMapper.selectDetail(8);
		log.info("boardInfo : {}", boardInfo);
	}

	@Test
	public void getDetail2() {
		BoardDto boardInfo = this.boardMapper.selectDetail(7);
		log.info("boardInfo : {}", boardInfo);
	}


}
