package io.ustra.framework2.sample.board;

import com.gsitm.ustra.java.core.utils.data.CodeValueType;

/**
 * 게시 구분 코드
 * @author RoyLee
 *
 */
public enum PostDivision implements CodeValueType {
	/**
	 * 공지사항
	 */
	NOTICE("01"),

	/**
	 * 일반게시
	 */
	NORMAL("02");

	@Override
	public String getCode() {
		return this.code;
	}

	private String code;

	private PostDivision(String code) {
		this.code = code;
	}
}
