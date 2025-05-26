package com.gsitm.ustra.java.swagger.api.example;

import com.gsitm.ustra.java.management.models.base.UstraManagementBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "그룹 코드")
    private String grpCd;

    @Schema(description = "상세 코드")
    private String dtlCd;

    @Schema(description = "코드명")
    private String cdNm;

    @Data
    public static class Criteria {
        @Schema(description = "사용 여부")
        private String useYn;
    }
}