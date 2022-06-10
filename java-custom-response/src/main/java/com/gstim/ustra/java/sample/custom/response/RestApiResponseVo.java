package com.gstim.ustra.java.sample.custom.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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