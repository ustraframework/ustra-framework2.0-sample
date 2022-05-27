package io.ustra.framework2.sample.sample.board;

import com.gsitm.ustra.java.management.models.base.UstraManagementBaseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SampleBoardModel extends UstraManagementBaseModel {

	private Integer boardIdx;
	private String categoryCd;
	private String title;
	private String content;

}
