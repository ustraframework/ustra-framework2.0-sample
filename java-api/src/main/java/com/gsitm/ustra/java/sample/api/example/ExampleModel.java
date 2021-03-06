package com.gsitm.ustra.java.sample.api.example;

import com.gsitm.ustra.java.management.models.base.UstraManagementBaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ExampleModel extends UstraManagementBaseModel {

	/**
	 * 그룹코드
	 */
    private String grpCd;

    /**
     * 상세 코드
     */
    private String dtlCd;

    /**
     * 코드명
     */
    private String cdNm;

    @Data
    public static class Criteria {
    	/**
    	 * 사용여부
    	 */
        private String useYn;
    }
}
