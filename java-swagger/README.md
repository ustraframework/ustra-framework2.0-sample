# U.STRA Framework - JAVA, Code Swagger 설정하기


## Objectives



## What You Need

- 약 15분 소요
- 선호하는 텍스트 편집기 혹은 IDE
- JDK 1.8 이상
- Gradle 6.x 버전 혹은 Maven 3.2 이상
- 코드를 IDE로 바로 가져오기 할 수 있다
    - Visual Studio Code에서 임포트 (방법 링크)
    - STS에서 임포트 (방법 링크)
    - IntelliJ에서 임포트 (방법 링크)
    

## How to complete this guide

- 처음부터 시작하기
    - 준비된 설정으로부터 U.STRA Framework - JAVA 프로젝트 시작하기 (링크)
    - CLI로 프로젝트 시작하기 (링크)
- 다운로드하여 실행하기
    - git clone [https://github.com/ustraframework/ustraframework-sample-java-api.git](https://github.com/ustraframework/ustraframework-sample-java-api.git)
    - GitHub에서 다운로드 [https://github.com/ustraframework/ustraframework-sample-java-api](https://github.com/ustraframework/ustraframework-sample-java-api)
    

## U.STRA Framework - JAVA API의 기본 응답 구조 확인하기



### 기본 어플리케이션 작성하기



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



- Model 작성

- Service 작성

- Repository 작성

- Mapper 작성

- Controller 작성


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
    

    

## 페이지 요청 목록 조회 API 작성하기



- Service 추가 작성

            
- Repository 추가 작성

        
- Mapper 추가 작성

- Controller 추가 작성

        

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

- 

## SEE ALSO

이 가이드 문서를 이해하는 데 도움이 될 추가 자료는 아래의 링크에서 확인할 수 있다.

주제별 참고 문서



연관 샘플

