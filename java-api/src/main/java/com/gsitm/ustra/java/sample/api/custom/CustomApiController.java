package com.gsitm.ustra.java.sample.api.custom;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@RestController
public class CustomApiController {

  @RequestMapping("/custom/code")
  public RestApiResponseVo getCodes()
  {
    return CommonCodeCustomResponseVo.builder()
        .cdNm("TEST")
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
        .cdNm("TEST")
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
