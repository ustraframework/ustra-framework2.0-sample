package com.gstim.ustra.java.sample.custom.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomRestResponseController
{

    @RequestMapping("/custom/code")
    public RestApiResponseVo getCodes()
    {
        return CommonCodeCustomResponseVo.builder()
                .cdNm("CD_NM")
                .dtlCd("DTL_CD")
                .grpCd("GRP_CD")
                .build();
    }

    @RequestMapping("/custom/exception")
    public CommonCodeCustomResponseVo getException()
    {
        if(true)
        {
            throw RestResponseCode.CANNOT_FIND.exception();
        }

        return CommonCodeCustomResponseVo.builder()
                .cdNm("CD_NM")
                .dtlCd("DTL_CD")
                .grpCd("GRP_CD")
                .build();
    }

    @Builder
    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CommonCodeCustomResponseVo extends RestApiResponseVo
    {
        /**
         * 그룹코드
         */
        private String grpCd;

        /**
         * 상세코드
         */
        private String dtlCd;

        /**
         * 코드명
         */
        private String cdNm;
    }
}
