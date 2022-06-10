# U.STRA Framework - JAVA, API 응답의 커스터마이징과 예외처리

> U.STRA Framework - JAVA에서 기본 응답 형식인 RestResult / RestErrResult를 사용자 정의 응답 형식으로 커스터마이징하고 이에 따른 예외 처리 방법을 설명한다.

# Objectives

U.STRA Framework - JAVA의 기본 응답 형식(RestResult / RestErrResult)을 사용자 정의 응답 형식으로 커스터마이징하는 어플리케이션을 작성한다.

# What You Need

- 약 15분 소요
- 선호하는 텍스트 편집기 혹은 IDE
- JDK 1.8 이상
- Gradle 6.x 버전 혹은 Maven 3.2 이상
- 코드를 IDE로 바로 가져오기 할 수 있다
    - Visual Studio Code에서 임포트 (방법 링크)
    - STS에서 임포트 (방법 링크)
    - IntelliJ에서 임포트 (방법 링크)

# How to complete this guide

- 처음부터 시작하기
    - 준비된 설정으로부터 U.STRA Framework - JAVA 프로젝트 시작하기 (링크)
    - CLI로 프로젝트 시작하기 (링크)
- 다운로드하여 실행하기
    - git clone [https://github.com/ustraframework/ustraframework-sample-java-custom-response.git](https://github.com/ustraframework/ustraframework-sample-java-custom-response.git)
    - GitHub에서 다운로드 [https://github.com/ustraframework/ustraframework-sample-java-custom-response](https://github.com/ustraframework/ustraframework-sample-java-custom-response.git)

# Writing an application

- Package를 생성; com.gsitm.ustra.java.sample.custom, com.gsitm.ustra.java.sample.config, com.gsitm.ustra.java.sample.response
- config 패키지 안에 CustomRestResponseConfiguration.java 클래스 생성하여 아래 내용을 입력한다.
    
    ```java
    package com.gstim.ustra.java.sample.custom.config;
     
    import com.gsitm.ustra.java.core.exception.UstraResponseCode;
    import com.gsitm.ustra.java.mvc.rest.error.RestBaseExceptionHandler;
    import com.gsitm.ustra.java.mvc.rest.utils.DefaultBodyWriteHandler;
    import com.gstim.ustra.java.sample.custom.response.RestApiResponseVo;
    import com.gstim.ustra.java.sample.custom.response.RestResponseCode;
    import org.apache.commons.lang3.StringUtils;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.validation.FieldError;
    import org.springframework.web.bind.MethodArgumentNotValidException;
     
    import javax.servlet.http.HttpServletRequest;
    import javax.validation.ConstraintViolationException;
    import java.util.List;
    import java.util.stream.Collectors;
     
    @Configuration
    public class CustomRestResponseConfiguration
    {
        @Bean
        public CustomRestResponseBodyWriteHandler restResponseBodyWriteHandler()
        {
            return new CustomRestResponseBodyWriteHandler();
        }
     
        @Bean
        public CustomRestBaseExceptionHandler customRestBaseExceptionHandler()
        {
            return new CustomRestBaseExceptionHandler();
        }
     
        public static class CustomRestResponseBodyWriteHandler extends DefaultBodyWriteHandler
        {
            @Override
            public Object getSuccessResult(Object body)
            {
                RestApiResponseVo resVo = null;
                if(body instanceof RestApiResponseVo)
                {
                    resVo = (RestApiResponseVo)body;
                }
                else
                {
                    resVo = new RestApiResponseVo();
                    resVo.setBody(body);
                }
     
                this.mapResCode(resVo, UstraResponseCode.SUCCESS.getCode(), null);
                this.storeResCode(resVo.getResultCode().toString());
     
                return resVo;
            }
     
            @Override
            public Object getFailureResult(String code, String message)
            {
                RestApiResponseVo resVo = new RestApiResponseVo();
                this.mapResCode(resVo, code, message);
                this.storeResCode(resVo.getResultCode().toString());
                return resVo;
            }
     
            private void mapResCode(RestApiResponseVo resVo, String code, String message)
            {
                if(resVo.getResultCode() != null)
                {
                    return;
                }
     
                RestResponseCode resCode = RestResponseCode.mapResponseCode(code);
                resVo.setResultCode(resCode.getNumberCode());
                resVo.setResultMsg(StringUtils.defaultString(message, resCode.getMessage()));
            }
        }
     
        public static class CustomRestBaseExceptionHandler extends RestBaseExceptionHandler
        {
            //  getExceptionResult?? getExcetionResult??
            @Override
            public Object getExcetionResult(HttpServletRequest request, Exception e)
            {
                RestApiResponseVo result = (RestApiResponseVo)super.getExcetionResult(request, e);
                this.storeResCode(result.getResultCode().toString());
     
                return result;
            }
     
            @Override
            protected Object getFailureResult(ConstraintViolationException e)
            {
                String message = e.getConstraintViolations().stream()
                        .map(fe->"[" + fe.getPropertyPath() + "] " + fe.getMessage())
                        .collect(Collectors.joining(","));
     
                RestApiResponseVo result = new RestApiResponseVo();
                result.setResultCode(RestResponseCode.INVALID_REQUEST_VALUE.getNumberCode());
                result.setResultMsg(message);
     
                return result;
            }
     
            @Override
            protected Object getFailureResult(MethodArgumentNotValidException e)
            {
                return getFailureResult(e.getBindingResult().getFieldErrors());
            }
     
            @Override
            protected Object getFailureResult(List<FieldError> e)
            {
                String message = e.stream()
                        .map(fe->"[" + fe.getField() + "] " + fe.getDefaultMessage())
                        .collect(Collectors.joining(","));
     
                RestApiResponseVo result = new RestApiResponseVo();
                result.setResultCode(RestResponseCode.INVALID_REQUEST_VALUE.getNumberCode());
                result.setResultMsg(message);
     
                return result;
            }
     
            @Override
            protected Object getFailureResult(String code, String message)
            {
                RestApiResponseVo result = new RestApiResponseVo();
                this.mapResCode(result, code, message);
                return result;
            }
     
            @Override
            protected Object getFailureResult(String code, String message, Exception e)
            {
                return getFailureResult(code, message);
            }
     
            private void mapResCode(RestApiResponseVo resVo, String code, String message)
            {
                if(resVo.getResultCode() != null)
                {
                    return;
                }
     
                RestResponseCode resCode = RestResponseCode.mapResponseCode(code);
                resVo.setResultCode(resCode.getNumberCode());
                resVo.setResultMsg(StringUtils.defaultString(message, resCode.getMessage()));
            }
     
        }
    }
    ```
    
    - @Configuration 태그를 이용하여 설정 파일을 생성
    - CustomRestResponseBodyWriteHandler와 CustomRestBaseExceptionHandler 빈(Bean)을 등록
    - CustomRestResponseBodyWriteHandler는 U.STRA Framework - JAVA의 DefaultBodyWriteHandler를 상속
    - CustomRestResponseBodyWriteHandler에 getSuccessResult(), getFailureResult() 메소드를 오버라이드
    - CustomRestBaseExceptionHandler는 U.STRA Framework - JAVA의 RestBaseExceptionHandler를 상속
    - CustomRestBaseExceptionHandler에 getExceptionResult(), getFailureResult() 메소드를 오버라이드
- response 패키지 안에 RestApiResponseVo.java, RestResponseCode.java, CustomRestResponseController.java 클래스를 생성하여 아래 내용을 입력한다.
    - RestApiResponse.java
        
        ```java
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
        ```
        
        - 예제로 출력할 응답 Vo 클래스로 사용자 정의 응답 포맷을 정의
        - lombok의 @Data 어노테이션 사용
- RestResponseCode.java
    
    ```java
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
    ```
    
    - 응답 코드를 정의
    - U.STRA Framework - JAVA의 ResponseCode 인터페이스를 구현한다.
    - getCode()와 getMessage()를 오버라이드
- CustomResponseController.java
    
    ```java
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
    ```
    
    - 이 어플리케이션을 테스트할 컨트롤러
    - /custom/code 를 이용하여 응답 형식을 확인 가능
    - /custom/exception 을 이용하여 예외 처리를 확인 가능
    - 앞서 작성한 RestApiResponseVo를 상속받는 CommonCodeCustomResponseVo 클래스 작성, lombok의 @Builder 어노테이션을 사용
    - getException()에서는 예외 처리를 보여주기 위해 무조건 예외를 던지는 코드로 작성
- application 작성; com.gsitm.ustra.java.sample.custom 안에 UstraJavaSampleCustomResponseRunner.java 클래스를 생성하여 아래 내용을 입력한다.
    - UstraJavaSampleCustomResponseRunner.java
        
        ```java
        package com.gstim.ustra.java.sample.custom;
         
        import com.gsitm.ustra.java.mvc.app.ServletApplicationRunner;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
         
        import java.io.IOException;
         
        @SpringBootApplication
        public class UstraJavaSampleCustomResponseRunner extends ServletApplicationRunner
        {
            public static void main(String[] args) throws IOException
            {
                ServletApplicationRunner.run(UstraJavaSampleCustomResponseRunner.class, args);
            }
        }
        ```
        
        - 어플리케이션을 실행하는 메인 메소드
        - U.STRA Framework - JAVA의 ServletApplicationRunner를 상속받아 구현

# Build an executable JAR

아래 명령어를 사용하여 어플리케이션을 빌드하고 실행한다.

- gradle
    
    ```bash
    ./gradlew bootRun
    ```
    
- maven
    
    ```bash
    ./mvnw spring-boot:run
    ```
    
- U.STRA Framework의 로그 내역이 출력되는 것을 확인한다.
- 아래의 명령을 입력하여 결과 값을 확인한다.
- 커스텀 응답 포맷의 확인:
    - 요청
        
        ```bash
        curl http://localhost:8981/custom/code
        ```
        
    - 결과
        
        ```bash
        {"resultCode":200,"resultMsg":"처리 완료","body":null,"grpCd":"GRP_CD","dtlCd":"DTL_CD","cdNm":"CD_NM"}
        ```
        
- 커스텀 응답 포맷 사용 시 예외 처리 확인 :
    - 요청
        
        ```bash
        curl http://localhost:8981/custom/exception
        ```
        
    - 결과
        
        ```bash
        {"resultCode":502,"resultMsg":"해당 조건에 맞는 내용을 찾을 수 없습니다.","body":null}
        ```
        

# Summary

이제 U.STRA Framewok - JAVA의 사용자 정의 응답 형태로 커스터마이징 되었고 이에 따른 예외 처리를 할 수 있게 되었다.

# SEE ALSO

이 가이드 문서를 이해하는 데 도움이 될 추가 자료는 아래의 링크에서 확인할 수 있다.

주제별 참고 문서

[API 커스톰 응답 처리](https://www.notion.so/API-ee122fa40c2846ae99e54041a4751506)

[API 만들기](https://www.notion.so/API-08d37fc99f7647a1a3fb40ff40840588)

[Exception 정의](https://www.notion.so/Exception-9f6d45d2b876417283a30c7bd7dec4d3)

연관 샘플

[U.STRA Framework - JAVA, API 만들기](https://www.notion.so/U-STRA-Framework-JAVA-API-60d5ab72fa4e407e810c8b726151b1a4)