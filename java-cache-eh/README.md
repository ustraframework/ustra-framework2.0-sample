# U.STRA Framework - JAVA, EHCACHE 사용하기
> U.STRA Framework - JAVA에서 지원하는 Cache 중 EhCache 사용 방법을 익힌다.

# Objectives

U.STRA Framework - JAVA에서 지원하는 EhCache를 사용하기 위한 설정 방법을 익히고 EhCache를 사용하는 어플리케이션을 작성한다.

# What You Need

- 약 15분 소요
- 선호하는 텍스트 편집기 혹은 IDE
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
    - GitHub에서 다운로드 [https://github.com/ustraframework/ustraframework-sample-java-ehcache](https://github.com/ustraframework/ustraframework-sample-java-ehcache)
    - git clone 으로 다운로드
        
        ```bash
        git clone https://github.com/ustraframework/ustraframework-sample-java-ehcache.git
        ```
        

# Writing an application

## EhCache 사용을 위한 설정

- src/main/resources/application.yml 파일에 아래 내용을 추가
    
    ```yaml
    ustra:
      core:
        cache:
          eh:
            eh-test:
              config-location: classpath:/ehcache.xml
    ```
    
    - EhCache 사용 설정을 추가
    - ehcache을 설정하는 파일의 위치를 classpath:/ehcache.xml로 설정

- src/main/resources/ehcache.xml 파일에 아래 내용을 추가
    
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <config xmlns="http://www.ehcache.org/v3">
       <!-- 캐시 파일이 생성되는 경로 -->
       <persistence directory="cache/data" />
       <!-- <cache-template name="template">-->
       <!-- 캐시가 생성되고 삭제되고 하는 이벤트를 모니터링 하고 싶으면 org.ehcache.event.CacheEventListener 를 구현하는 클래스를 만들어서 설정 (태그 순서가 중요)-->
       <!-- <listeners>-->
       <!-- <listener>-->
       <!-- <class>sample.CacheEventLogger</class>-->
       <!-- <event-firing-mode>ASYNCHRONOUS</event-firing-mode>-->
       <!-- <event-ordering-mode>UNORDERED</event-ordering-mode>-->
       <!-- <events-to-fire-on>CREATED</events-to-fire-on>-->
       <!-- <events-to-fire-on>EVICTED</events-to-fire-on>-->
       <!-- <events-to-fire-on>REMOVED</events-to-fire-on>-->
       <!-- <events-to-fire-on>UPDATED</events-to-fire-on>-->
       <!-- <events-to-fire-on>EXPIRED</events-to-fire-on>-->
       <!-- </listener>-->
       <!-- </listeners>-->
       <!-- </cache-template>-->
       <cache alias="ustraEhCache">
          <key-type>com.gsitm.ustra.java.sample.cache.eh.EhCacheModel.Criteria</key-type>
          <value-type>java.util.List</value-type>
          <expiry>
             <!-- 캐시 만료 시간 = timeToLiveSeconds -->
             <ttl unit="seconds">30</ttl>
          </expiry>
          <resources>
             <!-- JVM heap 메모리, LRU strategy-->
             <heap unit="entries">1000</heap>
             <!-- JVM heap 메모리 외부의 메모리 -->
             <!-- <offheap unit="MB">10</offheap>-->
             <!-- Disk 메모리, LFU strategy-->
             <!-- persistent="false" Ehcache will wipe the disk data on shutdown.-->
             <!-- persistent="true" Ehcache will preserve the disk data on shutdown and try to load it back on restart of the JVM.-->
             <disk unit="MB" persistent="false">5</disk>
          </resources>
       </cache>
    </config>
    ```
    
    - EhCache 사용 설정을 추가
    - ehcache을 설정하는 파일 ehcache.xml을 설정

## EhCache 사용을 위한 코드 작성

### Package 생성

- Package를 생성; /src/main/java에 com.gsitm.ustra.java.sample.cache.eh 생성

### Model 작성

- EhCacheModel.java를 생성하고 아래 내용을 작성한다.
    
    ```java
    @SuperBuilder
    @Data
    @EqualsAndHashCode(callSuper = false)
    @NoArgsConstructor
    @AllArgsConstructor
    public class EhCacheModel extends UstraManagementBaseModel implements Serializable {
    
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
    
      @Data
      public static class Criteria implements Serializable {
        /**
         * 사용여부
         */
        private String useYn;
      }
    }
    ```
    
    - DB 쿼리의 내용을 저장할 모델 클래스 정의
    - lombok의 @Data, @SuperBuilder, @EqualsAndHashCode(callSuper = false), @NoArgsConstructor, @AllArgsConstructor 어노테이션 사용
    - UstraManagementBaseModel을 상속
    - Serializable 인터페이스의 구현

### Service 작성

- EhCacheService.java 파일을 생성하고 아래 내용을 작성한다.
    
    ```java
    @Service
    @Transactional
    public class EhCacheService {
    
      public static final String EH_CACHE = "ustraEhCache";
      
      @Autowired private EhCacheRepository ehCacheRepository;
    
      @Cacheable(value=EH_CACHE, cacheManager="eh-test")
      public List<EhCacheModel> getAll(EhCacheModel.Criteria criteria) {
    
        return this.ehCacheRepository.selectAll(criteria);
      }
    
      @CacheEvict(value=EH_CACHE, allEntries = true)
      public String clearCache(EhCacheModel.Criteria criteria) {
    
        String strReturn = "\"" + EH_CACHE + "\" Cache cleared.";
        return strReturn;
      }
    }
    ```
    
    - Service를 구현한다.
    - getAll 메서드는 EhCacheModel의 리스트를 반환하는 서비스 메서드.
    - ehcache.xml에서 설정한 EhCache의 이름으로 캐시를 설정 @Cacheable(value="ustraEhCache")
    - application.yml에서 설정한 캐시매니저 eh-test
    - clearCache 메서드는 저장된 캐시를 삭제하는 메서드.

### Repository 작성

- EhCacheRepository.java 파일을 생성하고 아래 내용을 작성한다.
    
    ```java
    @Mapper
    public interface EhCacheRepository {
      
      List<EhCacheModel> selectAll(EhCacheModel.Criteria criteria);
    }
    ```
    
    - Repository 구현한다.
    - 공통 코드를 가져오는 쿼리와 매핑되는 Repository인 EhCacheRepository 인터페이스 선언.

### Mapper XML 작성

- /src/main/resources/mapper/cache/EhCacheMapper.xml 파일을 생성하고 아래 내용을 작성한다.
    
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    
    <mapper namespace="com.gsitm.ustra.java.sample.cache.eh.EhCacheRepository">
    
        <select id="selectAll" resultType="com.gsitm.ustra.java.sample.cache.eh.EhCacheModel">
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
    
    - Mapper XML을 구현한다.
    - com.gsitm.ustra.java.sample.cache.eh.EhCacheRepository 인터페이스와 매핑되며 com.gsitm.ustra.java.sample.cache.eh.EhCacheModel 형태의 리스트를 반환.

### Controller 작성

- EhCacheController.java 파일을 생성하고 아래 내용을 작성한다.
    
    ```java
    @RestController
    @RequestMapping("/api/cache/eh")
    public class EhCacheController {
      
      @Autowired EhCacheService ehCacheService;
    
      @GetMapping()
      public List<EhCacheModel> getAll(String useYn) {
        EhCacheModel.Criteria criteria = new EhCacheModel.Criteria();
        criteria.setUseYn(useYn);
    
        return this.ehCacheService.getAll(criteria);
      }
    
      @GetMapping("/clear-cache")
      public String clearCache(String useYn) {
        EhCacheModel.Criteria criteria = new EhCacheModel.Criteria();
        criteria.setUseYn(useYn);
    
        return this.ehCacheService.clearCache(criteria);
      }
    }
    ```
    
    - EhCache의 동작을 확인할 컨트롤러를 작성.
    - /api/cache/eh를 호출하여 EhCache의 동작을 확인한다.
    - /api/cache/eh/clear-cache를 호출하여 EhCacheService의 clearCache 메서드를 실행.

### Application 작성

- com.gsitm.ustra.java.sample.cache 안에 UstraJavaSampleEhRunner.java 클래스를 생성하여 아래 내용을 입력한다.
    
    ```java
    @SpringBootApplication
    public class UstraJavaSampleEhRunner extends ServletApplicationRunner {
    
      public static void main(String[] args) throws IOException {
        ServletApplicationRunner.run(UstraJavaSampleEhRunner.class, args);
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
    

U.STRA Framework의 로그 내역이 출력되는 것을 확인한다.

- 아래의 명령을 입력하여 결과 값을 확인한다.
    - 최초의 요청은 Cache 되기 전, 아래 요청으로 응답 속도를 확인한다.
    - 최초 요청
        
        ```bash
        curl http://localhost:8981/api/cache/eh -w "\n\n==== cURL measurements stats ====\ntotal: %{time_total} seconds \nsize: %{size_download} bytes \ndnslookup: %{time_namelookup} seconds \nconnect: %{time_connect} seconds \nappconnect: %{time_appconnect} seconds \nredirect: %{time_redirect} seconds \npretransfer: %{time_pretransfer} seconds \nstarttransfer: %{time_starttransfer} seconds \ndownloadspeed: %{speed_download} byte/sec \nuploadspeed: %{speed_upload} byte/sec \n\n"
        ```
        
    - 최초 결과
        
        ```bash
        {"header":{"currentPage":0,"pageSize":0,"totalRecords":0,"orders":null,"chnlCd":null},"resultCode":"0000","resultMessage":"처리 완료","body":[{"regDttm":"2022-05-09T14:52:56.9956252","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-05-09T14:52:56.9956252","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"APV_STT_CD","dtlCd":"*","cdNm":"승인 상태"},{"regDttm":"2022-05-09T14:52:56.9956252","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-05-09T14:52:56.9956252","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"APV_STT_CD","dtlCd":"01","cdNm":"요청"}, ... 결과 생략 ... {"regDttm":"2022-05-09T14:52:57.0096237","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-05-09T14:52:57.0096237","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"WORK_DV_CD","dtlCd":"I","cdNm":"신규"},{"regDttm":"2022-05-09T14:52:57.0096237","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-05-09T14:52:57.0096237","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"WORK_DV_CD","dtlCd":"U","cdNm":"수정"}],"hasError":false}
        
        ==== cURL measurements stats ====
        total: 2.196007 seconds
        size: 26974 bytes
        dnslookup: 0.000049 seconds
        connect: 0.000676 seconds
        appconnect: 0.000000 seconds
        redirect: 0.000000 seconds
        pretransfer: 0.000756 seconds
        starttransfer: 2.186833 seconds
        downloadspeed: 12283 byte/sec
        uploadspeed: 0 byte/sec
        ```
        
    - Cache된 후 요청 (최초의 요청과 같은 요청)
        
        ```bash
        curl http://localhost:8981/api/cache/eh -w "\n\n==== cURL measurements stats ====\ntotal: %{time_total} seconds \nsize: %{size_download} bytes \ndnslookup: %{time_namelookup} seconds \nconnect: %{time_connect} seconds \nappconnect: %{time_appconnect} seconds \nredirect: %{time_redirect} seconds \npretransfer: %{time_pretransfer} seconds \nstarttransfer: %{time_starttransfer} seconds \ndownloadspeed: %{speed_download} byte/sec \nuploadspeed: %{speed_upload} byte/sec \n\n"
        ```
        
    - Cache된 후의 결과
        
        ```bash
        {"header":{"currentPage":0,"pageSize":0,"totalRecords":0,"orders":null,"chnlCd":null},"resultCode":"0000","resultMessage":"처리 완료","body":[{"regDttm":"2022-05-09T14:52:56.9956252","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-05-09T14:52:56.9956252","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"APV_STT_CD","dtlCd":"*","cdNm":"승인 상태"},{"regDttm":"2022-05-09T14:52:56.9956252","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-05-09T14:52:56.9956252","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"APV_STT_CD","dtlCd":"01","cdNm":"요청"}, ... 결과 생략 ... {"regDttm":"2022-05-09T14:52:57.0096237","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-05-09T14:52:57.0096237","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"WORK_DV_CD","dtlCd":"I","cdNm":"신규"},{"regDttm":"2022-05-09T14:52:57.0096237","regUsrId":"Anonymous","regUsrIp":"127.0.0.1","updDttm":"2022-05-09T14:52:57.0096237","updUsrId":"Anonymous","updUsrIp":"127.0.0.1","grpCd":"WORK_DV_CD","dtlCd":"U","cdNm":"수정"}],"hasError":false}
        
        ==== cURL measurements stats ====
        total: 0.058739 seconds
        size: 26965 bytes
        dnslookup: 0.000061 seconds
        connect: 0.000772 seconds
        appconnect: 0.000000 seconds
        redirect: 0.000000 seconds
        pretransfer: 0.000821 seconds
        starttransfer: 0.051649 seconds
        downloadspeed: 459064 byte/sec
        uploadspeed: 0 byte/sec
        ```
        

- Cache가 적용되기 전과 후의 응답 속도를 확인한다.
- 또한, IDE의 디버그 콘솔 로그를 확인하여 DB 쿼리 여부를 확인하여 Cache 사용 여부를 확인할 수 있다.
- /api/cache/eh/clear-cache API를 호출하고 다시 조회 요청하여 응답 속도 및 IDE의 디버그 콘솔 로그를 확인해본다.

# Summary

- U.STRA Framework에서 지원하는 EhCache의 사용 방법을 익혀 어플리케이션의 성능 향상을 기대할 수 있다.

# SEE ALSO

이 가이드 문서를 이해하는 데 도움이 될 추가 자료는 아래의 링크에서 확인할 수 있다.

- 주제별 참고 문서
    
    [캐시](https://www.notion.so/beba29f43ec743fda6ff903117cc3082)
    
    [시작하기](https://www.notion.so/a67cdf40981d40c781490bb91b543035)
    
    [SQL Map XML 파일](https://www.notion.so/SQL-Map-XML-99dd2c5807f34d2690b3907a26a0defe)
    
    [Redis](https://www.notion.so/Redis-e2f6182f488744a5855b931239f58498)
    
    [API 만들기](https://www.notion.so/API-08d37fc99f7647a1a3fb40ff40840588)
    
    [Model / Service / Controller](https://www.notion.so/Model-Service-Controller-4c5f4edb230b4be18e23578db1dca0c7)
    
    [어플리케이션 구동 (Main Class)](https://www.notion.so/Main-Class-54ed09805bfb4862b597c0f537b0b416)
    
    [모델 매핑](https://www.notion.so/f4cb33edbb744538b13e7f2d4ff20f67)
    
- 연관 샘플
    
    [U.STRA Framework - JAVA, API 만들기](https://www.notion.so/U-STRA-Framework-JAVA-API-60d5ab72fa4e407e810c8b726151b1a4)
    
    [U.STRA Framework - Redis](https://www.notion.so/U-STRA-Framework-Redis-9561e69fa07348bd965d33cae80ae070)
    
    [U.STRA Framework - JAVA, CAFFEINE CACHE 사용하기](https://www.notion.so/U-STRA-Framework-JAVA-CAFFEINE-CACHE-6c085770044344daa4888001f3521250)
