package com.gstim.ustra.java.sample.custom.response;

import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name="resultSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestApiResponseVo {
    /**
     * 결과 코드
     */
    @XmlElement
    private Integer resultCode;

    /**
     * 결과 메시지
     */
    @XmlElement
    private String resultMsg;

    /**
     * 일반 API 결과
     */
    private Object body;

}