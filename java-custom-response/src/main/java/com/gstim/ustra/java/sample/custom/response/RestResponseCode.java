package com.gstim.ustra.java.sample.custom.response;


import com.gsitm.ustra.java.core.exception.ResponseCode;
import com.gsitm.ustra.java.core.exception.UstraResponseCode;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

public enum RestResponseCode implements ResponseCode {

    SUCCESS("200", "처리 완료"),
    INVALID_REQUEST_VALUE("100", "잘못된 입력입니다."),
    CANNOT_FIND("502", "해당 조건에 맞는 내용을 찾을 수 없습니다."),
    NOTIFY_ERROR_MESSAGE("999", "에러 메시지 노출");

    private String code;
    private String message;

    RestResponseCode(String code, String message)
    {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode()
    {
        return this.code;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }

    /**
     * 정수 유형의 결과 코드 값 조회
     * @return 코드 값을 정수 형태로 반환
     */
    public Integer getNumberCode()
    {
        return Integer.parseInt(this.code);
    }

    /**
     * 코드로 RestResponseCode 조회
     * @param code
     * @return
     */
    public static RestResponseCode from(String code)
    {
        return Stream.of(RestResponseCode.values()).filter(rc->rc.code.equals(code))
                .findFirst().orElse(null);
    }

    /**
     * 표준 응답 코드를 변환 처리한다.
     * @return
     */
    public static RestResponseCode mapResponseCode(String code)
    {
        if(StringUtils.isEmpty(code))
        {
            return RestResponseCode.SUCCESS;
        }

        //  매핑 코드 존재 시, 동일 코드 값 반환
        RestResponseCode equalResponseCode = RestResponseCode.from(code);
        if(equalResponseCode != null)
        {
            return equalResponseCode;
        }

        if(UstraResponseCode.isSuccess(code))
        {
            return RestResponseCode.SUCCESS;
        }

        return RestResponseCode.NOTIFY_ERROR_MESSAGE;
    }
}
