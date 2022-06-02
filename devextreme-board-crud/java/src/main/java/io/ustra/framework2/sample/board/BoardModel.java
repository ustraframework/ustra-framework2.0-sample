package io.ustra.framework2.sample.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.gsitm.ustra.java.data.validation.CrudGroups;
import com.gsitm.ustra.java.management.models.base.UstraManagementBaseModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BoardModel extends UstraManagementBaseModel {

	/**
	 * 게시 아이디
	 */
	@Positive(message = "게시 아이디는 필수 입력입니다.", groups=CrudGroups.Edit.class)
	@NotNull(message = "게시 아이디는 필수 입력입니다.", groups=CrudGroups.Edit.class)
	private Integer postId;

	/**
	 * 저자 명
	 */
	@NotEmpty(message = "작성자 명은 필수 입력입니다.", groups= { CrudGroups.Add.class, CrudGroups.Edit.class })
	private String autNm;

	/**
	 * 제목
	 */
	@NotEmpty(message = "제목은 필수 입력입니다.", groups= { CrudGroups.Add.class, CrudGroups.Edit.class })
	private String title;

	/**
	 * 내용
	 */
	@NotEmpty(message = "내용은 필수 입력입니다.", groups= { CrudGroups.Add.class, CrudGroups.Edit.class })
	private String cont;

	/**
	 * 게시 구분 코드
	 */
	@NotNull(message = "게시 구분 코드는 필수 입력입니다.", groups= { CrudGroups.Add.class, CrudGroups.Edit.class })
	private PostDivision postDvCd;

	/**
	 * 조회 수
	 */
	private Integer inqNum;

	/**
	 * 삭제 여부
	 */
	private String delYn;

	/**
	 * 파일 아이디
	 */
	private String fileId;

	/**
	 * 검색 조건
	 * @author RoyLee
	 *
	 */
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Criteria {
		/**
		 * 검색 키워드
		 */
		private String keyword;

		/**
		 * 검색 시작일
		 */
		private LocalDateTime srtDate;

		/**
		 * 검색 종료일
		 */
		private LocalDateTime endDate;
	}
}
