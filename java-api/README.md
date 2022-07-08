# U.STRA Framework - JAVA, API 만들기
> U.STRA Framwork에서 제공하는 기본 응답 포맷을 확인하고 간단한 조회를 실행한다.

## Objectives

U.STRA Framework에서 제공하는 기본 응답 포맷인 RestResult / RestErrResult를 확인하는 API를 작성하고 코드 목록을 조회하는 어플리케이션을 작성한다.

## What You Need

- 약 15분 소요
- 선호하는 텍스트 편집기 혹은 IDE
- JDK 1.8 이상
- Gradle 6.x 버전 혹은 Maven 3.2 이상
    

## How to complete this guide

- 처음부터 시작하기
    - 프로젝트 시작하기 : [http://guide.ustraframework.kro.kr/download/installation](http://guide.ustraframework.kro.kr/download/installation)
- 다운로드하여 실행하기
    - Repository에서 Clone : [https://github.com/ustraframework/ustra-framework2.0-sample](https://github.com/ustraframework/ustra-framework2.0-sample)

# 예제 파일 다운로드

아래와 같이 github에서 샘플 전체를 다운로드 할 수 있으며, 다운로드 후 devextrme-board-crud 디렉토리로 이동한다.

```
git clone https://github.com/ustraframework/ustra-framework2.0-sample.git
```

- 코드 위치 : [https://github.com/ustraframework/ustra-framework2.0-sample/tree/master/java-api](https://github.com/ustraframework/ustra-framework2.0-sample/tree/master/java-api)



## U.STRA Framework - JAVA API의 기본 응답 구조 확인하기

기본적인 응답 구조만 확인하는 어플리케이션의 작성

### 기본 어플리케이션 작성하기

- Package를 생성; com.gsitm.ustra.java.sample.api.greeting 패키지를 생성
- 생성한 패키지 안에 GreetingController.java 컨트롤러 생성
    
    ```java
    package com.gsitm.ustra.java.sample.api.greeting;
     
    import io.swagger.annotations.Api;
    import io.swagger.annotations.ApiOperation;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.ResponseBody;
    import org.springframework.web.bind.annotation.RestController;
     
    @Api("Greetings API")
    @RestController
    @RequestMapping("/api/greeting")
    public class GreetingController {
        @GetMapping
        @ApiOperation(value = "Greetings", notes = "Greetings from U.STRA")
        @ResponseBody
        public String getGreeting() {
            return "Hello, U.STRA!";
        }
    }
    ```
    
    - 클래스 선언 앞에 @RequestMapping 어노테이션으로 이 컨트롤러의 기본 URI를 /api/greeting 으로 설정
    - RestResult의 구조를 확인하는 것이 목적이므로 단순 문자열만 응답한다.
    - application 작성; com.gsitm.ustra.java.sample.api 안에 UstraJavaSampleApiRunner.java 클래스를 생성하여 아래 내용을 입력한다.
- UstraJavaSampleApiRunner.java
    
    ```java
    package com.gsitm.ustra.java.sample.api;
     
    import java.io.IOException;
     
    import org.springframework.boot.autoconfigure.SpringBootApplication;
     
    import com.gsitm.ustra.java.mvc.app.ServletApplicationRunner;
     
    @SpringBootApplication
    public class UstraJavaSampleApiRunner extends ServletApplicationRunner {
        public static void main(String[] args) throws IOException {
            ServletApplicationRunner.run(UstraJavaSampleApiRunner.class, args);
        }
    }
    ```
    
    - 어플리케이션을 실행하는 메인 메소드
    - U.STRA Framework - JAVA의 ServletApplicationRunner를 상속받아 구현

### 기본 어플리케이션 빌드 및 실행

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
    - U.STRA Framework - JAVA의 API 기본 응답 포맷을 확인한다.
- 요청
    
    ```bash
    curl http://localhost:8981/api/greeting
    ```
    
- 결과
    
    ```bash
    {"header":{"currentPage":0,"pageSize":0,"totalRecords":0,"orders":null,"chnlCd":null},"resultCode":"0000","resultMessage":"처리 완료","body":"Hello, U.STRA!","hasError":false}
    ```
    

## **전체 목록 조회 API 작성 하기**

기본 프로젝트에 전체 목록을 조회하는 API를 추가한다. com.gsitm.ustra.java.sample.api 아래에 example 패키지를 추가하여 작성한다

- Model 작성
    - ExampleModel.java 파일을 생성하여 아래 내용을 입력한다. 이 Model 클래스는 기본 조회 API, 페이징 조회 API에서 사용할 수 있다.
        
        ```java
        package com.gsitm.ustra.java.sample.api.example;
         
        import com.gsitm.ustra.java.management.models.base.UstraManagementBaseModel;
        import io.swagger.annotations.ApiModelProperty;
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
         
            @ApiModelProperty("그룹 코드")
            private String grpCd;
         
            @ApiModelProperty("상세 코드")
            private String dtlCd;
         
            @ApiModelProperty("코드명")
            private String cdNm;
         
            @Data
            public static class Criteria {
                @ApiModelProperty("사용여부")
                private String useYn;
            }
        }
        ```
        
        - 모델 클래스는 UstraManagementBaseModel을 상속
        - lombok의 @SuperBuilder, @Data, @EqualsAndHashCode, @NoArgsConstructor, @AllArgsConstructor 사용
- Service 작성
    - ExampleService.java 클래스 작성
        
        ```java
        package com.gsitm.ustra.java.sample.api.example;
         
        import com.gsitm.ustra.java.data.mybatis.proxy.MapperManager;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;
         
        import java.util.List;
         
        @Service
        @Transactional
        public class ExampleService {
         
            @Autowired private ExampleMapper exampleMapper;
         
            public List<ExampleModel> getAll(ExampleModel.Criteria criteria) {
                return this.exampleMapper.selectAll(criteria);
            }
        }
        ```
        
        - 일반적인 Service 작성과 동일
        - Repository 클래스로 ExampleMapper를 @Autowired
        - ExampleModel의 List를 리턴하는 메서드 작성
- Repository 작성
    - ExampleMapper.java 인터페이스 작성
        
        ```java
        package com.gsitm.ustra.java.sample.api.example;
         
        import org.apache.ibatis.annotations.Mapper;
         
        import java.util.List;
         
        @Mapper
        public interface ExampleMapper {
         
            List<ExampleModel> selectAll(ExampleModel.Criteria criteria);
        }
        ```
        
        - @Mapper를 사용하는 일반적인 Repository 작성과 동일
        - ExampleModel의 List를 리턴하는 인터페이스 선언
- Mapper 작성
    - src/main/resource/mapper/example/example 디렉토리에 ExampleMapper.xml 파일 생성 후 아래 내용 입력. (디렉토리가 없으면 새로 생성)
        
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
         
        <mapper namespace="com.gsitm.ustra.java.sample.api.example.ExampleMapper">
         
            <select id="selectAll" resultType="com.gsitm.ustra.java.sample.api.example.ExampleModel">
                SELECT GRP_CD
                    ,DTL_CD
                    ,CD_NM
                FROM USTRA_SAMPLE_CMM_CD
                WHERE 1=1
                <if test="useYn != null">
                    AND USE_YN = #{useYn}
                </if>
            </select>
         
        </mapper>
        ```
        
        - Repository에서 선언한 인터페이스 메소드에 대응하는 selectAll에 대한 쿼리 작성
        - resultType은 이 가이드에서 작성한 모델(com.gsitm.ustra.java.sample.api.example.ExampleModel로 지정
- Controller 작성
    - example 패키지에 [ExampleController.java](http://ExampleController.java) 파일 생성 및 작성
        
        ```java
        package com.gsitm.ustra.java.sample.api.example;
         
        import java.util.List;
         
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
         
        import io.swagger.annotations.Api;
        import io.swagger.annotations.ApiOperation;
        import io.swagger.annotations.ApiParam;
         
        @Api("U.STRA Examples for API")
        @RestController
        @RequestMapping("/api/example")
        public class ExampleController {
         
            @Autowired
            private ExampleService exampleService;
         
            @GetMapping("/common/code")
            @ApiOperation(value = "전체 코드 목록 조회", notes = "<strong>코드 전체 목록</string>을 반환")
            public List<ExampleModel> getAll(@ApiParam("사용 여부") String useYn) {
         
                ExampleModel.Criteria criteria = new ExampleModel.Criteria();
                criteria.setUseYn(useYn);
         
                return this.exampleService.getAll(criteria);
            }
        }
        ```
        
        - 일반적인 Controller와 작성 방법 동일
        - @RestController 어노테이션 사용
        - @RequestMapping 어노테이션으로 기본 URI를 /api/example 로 설정
        - 전체 목록 조회 URI를 @GetMapping 어노테이션 사용하여 /common/code 로 설정

### 전체 목록 조회 API 빌드 및 실행

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
    - 전체 목록 조회 API를 호출하여 결과를 확인한다.
- 요청
    
    ```bash
    curl http://localhost:8981/api/example/common/code
    ```
    
- 결과
    
    ```bash
    {"header":{"currentPage":0,"pageSize":0,"totalRecords":0,"orders":null,"chnlCd":null},"resultCode":"0000","resultMessage":"처리 완료","body":[{"regDttm":"2022-04-27T17:58:55.9716513","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-04-27T17:58:55.9716513","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"APV_STT_CD","dtlCd":"*","cdNm":"승인 상태"},{"regDttm":"2022-04-27T17:58:55.9716513","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-04-27T17:58:55.9726058","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"APV_STT_CD","dtlCd":"01","cdNm":"요청"},{"regDttm":"2022-04-27T17:58:55.9726058","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-04-27T17:58:55.9726058","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"APV_STT_CD","dtlCd":"02","cdNm":"승인"},{"regDttm":"2022-04-27T17:58:55.9726058","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-04-27T17:58:55.9726058","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"APV_STT_CD","dtlCd":"03","cdNm":"반려"},{"regDttm":"2022-04-27T17:58:55.9726058","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-04-27T17:58:55.9726058","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"APV_STT_CD","dtlCd":"04","cdNm":"삭제"},
    ... 결과 생략 ...
    {"regDttm":"2022-04-27T17:58:55.985668","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-04-27T17:58:55.985668","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"WORK_DV_CD","dtlCd":"D","cdNm":"삭제"},{"regDttm":"2022-04-27T17:58:55.985668","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-04-27T17:58:55.985668","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"WORK_DV_CD","dtlCd":"I","cdNm":"신규"},{"regDttm":"2022-04-27T17:58:55.985668","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-04-27T17:58:55.985668","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"WORK_DV_CD","dtlCd":"U","cdNm":"수정"}],"hasError":false}
    ```
    

## 페이지 요청 목록 조회 API 작성하기

이전 가이드의 전체 목록 조회 관련 클래스에 페이지 요청 목록 조회 API를 추가한다.

- Service 추가 작성
    - ExampleService.java 에 아래 내용을 추가한다.
        
        ```java
        public PaginationList<ExampleModel> getCodes(PaginationRequest request) {
            return MapperManager.get(ExampleMapper.class).getCodes(request);
        }
        ```
        
        - ExampleModel의 PaginationList를 반환하는 메소드 작성
        - U.STRA Framework의 PaginationRequest를 인자를 받아 Repository에 전달
        - 새로운 메서드에 필요한 패키지를 U.STRA Framework에서 import
            
            ```java
            import com.gsitm.ustra.java.data.domains.PaginationList;
            import com.gsitm.ustra.java.data.domains.PaginationRequest;
            import com.gsitm.ustra.java.data.mybatis.proxy.MapperManager;
            ```
            
- Repository 추가 작성
    - ExampleMapper.java 인터페이스에 아래 내용을 추가
        
        ```java
        PaginationList<ExampleModel> getCodes(PaginationRequest pageBound);
        ```
        
        - getCodes에 매핑
        - ExampleModel의 PaginationList 반환하며 PaginationRequest를 인자로 받는다
        - 필요한 패키지를 U.STRA Framework로 부터 import
        
        ```java
        import com.gsitm.ustra.java.data.domains.PaginationList;
        import com.gsitm.ustra.java.data.domains.PaginationRequest;
        ```
        
- Mapper 추가 작성
    - /src/main/resources/mapper/example/ExampleMapper.xml 파일에 아래 내용 추가
        
        ```xml
        <select id="getCodes" resultType="com.gsitm.ustra.java.sample.api.example.ExampleModel">
            SELECT GRP_CD,
                   DTL_CD,
                   CD_NM
            FROM USTRA_SAMPLE_CMM_CD
        </select>
        ```
        
        - Repository의 getCode에 매핑
        - com.gsitm.ustra.java.sample.api.example.ExampleModel 형태로 결과 값 반환
- Controller 추가 작성
    - example 패키지의 ExampleController.java 파일에 아래 내용을 추가
        
        ```java
        @PostMapping("/common/code/page")
        @ApiOperation(value = "페이지별 코드 목록 조회", notes = "페이지별 코드 목록을 반환")
        public PaginationList<ExampleModel> getCodesByPage(@RequestBody PaginationRequest request) {
            return this.exampleService.getCodes(request);
        }
        ```
        
        - @PostMapping 어노테이션으로 /common/code/page URI 매핑
        - @RequestBody로 PaginationRequest 객체를 받아 처리
        - ExampleModel의 PaginationList를 리턴
        - U.STRA Framework의 패키지와 spring framework의 패키지를 import
        
        ```java
        import com.gsitm.ustra.java.data.domains.PaginationList;
        import com.gsitm.ustra.java.data.domains.PaginationRequest;
         
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        ```
        

### 페이지 요청 목록 조회 API 빌드 및 실행

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
    - 페이지 목록 요청 API를 호출하여 결과를 확인한다.
- 요청
    
    ```bash
    curl -X POST -H "Content-Type: application/json; charset=utf-8" -d '{"currentPage":5,"pageSize":5,"orders":[{"name":"CD_NM","direction":"ASC"}]}' http://localhost:8981/api/example/common/code/page
    ```
    
- 결과
    
    ```bash
    {"header":{"currentPage":0,"pageSize":0,"totalRecords":0,"orders":null,"chnlCd":null},"resultCode":"FC02","resultMessage":"JSON parse error: Unexpected character (''' (code 39)): expected a valid value (JSON String, Number, Array, Object or token 'null', 'true' or 'false'); nested exception is com.fasterxml.jackson.core.JsonParseException: Unexpected character (''' (code 39)): expected a valid value (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\n at [Source: (PushbackInputStream); line: 1, column: 2]","body":null,"hasError":true,"errors":null}
    ```
    

## Summary

- 이제 U.STRA Framewok - JAVA의 API 작성 방법, API의 기본 응답 포맷인 RestResult/RestErrResult의 구조, 전체 목록 조회, 페이징 목록 조회를 할 수 있다.

## SEE ALSO

이 가이드 문서를 이해하는 데 도움이 될 추가 자료는 아래의 링크에서 확인할 수 있다.

주제별 참고 문서

[API 만들기](https://www.notion.so/API-08d37fc99f7647a1a3fb40ff40840588)

[Model / Service / Controller](https://www.notion.so/Model-Service-Controller-4c5f4edb230b4be18e23578db1dca0c7)

[Pagination](https://www.notion.so/Pagination-a8348ccdb2524b7db53303d8c239d618)

[어플리케이션 구동 (Main Class)](https://www.notion.so/Main-Class-54ed09805bfb4862b597c0f537b0b416)

[시작하기](https://www.notion.so/a67cdf40981d40c781490bb91b543035)

[SQL Map XML 파일](https://www.notion.so/SQL-Map-XML-99dd2c5807f34d2690b3907a26a0defe)

[모델 매핑](https://www.notion.so/f4cb33edbb744538b13e7f2d4ff20f67)

[Code Swagger](https://www.notion.so/Code-Swagger-e404e7e371da40c5b881f044f3496f55)

연관 샘플

[U.STRA Framework - JAVA, API 응답의 커스터마이징과 예외처리](https://www.notion.so/U-STRA-Framework-JAVA-API-a2518cdc824d41239668c54970f68e79) 