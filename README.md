KakaoCoupon 프로젝트
=
#1 문제해결 전략
-
- 요구사항 분석을 통한 계획 수립
  >(1) 개발언어는 Java, 프레임워크는 Spring-boot, IDE는 IntelliJ로 결정   
  
  >(2) SPA(Single Page Application), Rest API, AngularJS, JPA 관련 참고자료 조사  
  
  >(3) 이메일 입력 시에는 형식 검사, 중복 체크, 쿠폰번호 생성, 쿠폰 리스트 및 페이지 갱신 순으로 처리 필요  
  
  >(4) 라이브러리를 사용하지 않은 쿠폰번호 생성 방법에 대한 아이디어 정리 및 결정  
  >>문자 배열을 만들고 그 인덱스를 랜덤으로 지정하여 쿠폰번호 생성
  
  >(5) 데이터베이스는 In-memory db로 결정하고 IntelliJ를 통한 이용 방법 조사
  
  >(6) 쿠폰번호 리스팅 시의 Pagination 구현에 대한 JPA 구현 방법 조사
  
  >(7) 서버와 클라이언트 간의 JSON Object 통신을 위한 @RestController, @ResponseBody, @RequestParam 등 조사
  
  >(8) 단위 테스트는 Repository/Service/Controller Junit 테스트 코드 작성
  >>1. 이슈: Junit 테스트 코드 실행 시, Repository 쪽은 성공했지만 null pointer 오류로 인해 Service 쪽은 test case 일부만 성공했고 Controller 쪽은 모두 실패
  >>1. 원인: Service와 Controller 쪽에서는 Repository 연동에 문제가 있어서 save(), findAll() 등의 기능이 제대로 실행되지 않음
  
- 프로젝트 구조 관련 주요 구성요소(기술, 의존성, 라이브러리 등) 정리
  >(1) Spring boot
  
  >(2) SPA(Single Page Application)
  >>1. 서버 쪽에는 Rest API
  >>1. 클라이언트 쪽에는 AngularJS(라이브러리 필요), HTML(정적인 페이지 역할), Javascript 
  
  >(3) In memory db
  >>1. H2
  >>2. JPA
  
  >(4) Lombok (Getter/Setter 자동 생성 및 로깅 목적)
   
- 구현해야 하는 주요 기능 정의
  >(1) 이메일 중복 체크
  >>쿠폰 생성 API 호출 시, 이메일 중복 체크 시행.
  
  >(2) 중복되지 않은 랜덤 쿠폰번호 생성
  >>쿠폰 생성 API 호출 시, 이메일 중복 체크 후 시행.  
  >>생성 방법은 문자 배열을 만들고 그 인덱스를 랜덤으로 지정하여 쿠폰번호 생성.
 
  >(3) 쿠폰 정보 listing 및 pagination
  >>JPA가 제공하는 리스팅 및 페이징 기능 이용.

- 서버 쪽에 필요한 Rest API 종류 정의
  >(1) 쿠폰 생성 API
  >>Method: POST
  >>URL: /coupons
  >>Parameters: Json
  >>Return: Json
  
  >(2) 페이지 정보에 해당하는 쿠폰 리스트 조회 API
  >>Method: GET
  >>URL: /coupons
  >>Parameters: String 쿼리 형식으로 넘어와 객체에 매핑
  >>Return: Json

- 클라이언트 쪽에 필요한 HTML, AngularJS 파일 정의
  >(1) index.html
  >>역할: 정적인 메인 페이지
  
  >(2) list.html
  >>역할: index.html 페이지 안의 내용에 해당하는 템플릿
  
  >(3) app.js
  >>역할: 싱글 페이지 'index.html' 접근에 대한 라우팅 처리
  
  >(4) CouponController.js
  >>역할: 컨트롤러로서 싱글 페이지의 이벤트 처리 및 데이터 바인딩 

  >(5) CouponService.js
  >>역할: 서비스로서 'CouponController.js'에서 수행해야 하는 작업 수행 

#2 프로젝트 빌드 및 실행 방법
-
- JDK, Tomcat, Gradle, IntelliJ 설치
  >설치과정에 대한 설명은 생략하겠습니다.
  
- IntelliJ 실행  
  >(1) 'Check out from Version Control' 클릭  
  >>1. 'Git' 클릭  
  >>1. Git Repository URL: https://github.com/soohyeon317/KakaoCoupon.git  
  >>1. Parent Directory: (프로젝트가 위치할 부모 디렉토리 경로 지정)  
  >>1. Directory Name: KakaoCoupon  
  >>1. 'Clone' 클릭  
  
  >(2)  Gradle 설정 (자동으로 설정창이 안 뜨셨으면 'Event Log' 탭에서 해당 메세지를 찾아 클릭하여 설정창 띄워주세요)
  >>1. 'Use auto-import' 체크  
  >>1. 'Use local gradle distribution' 클릭  
  >>1. Gradle home: (설치한 Gradle 디렉토리 경로 지정)  
  >>1. Gradle JVM: (설치한 JDK 디렉토리 경로 지정)   
  
  >(3) 'File' 메뉴 클릭  
  >>1. 'Settings..' 클릭  
  >>1. 'Annotation Processors' 클릭  
  >>1. 'Enable annotation processing' 체크    
  >>1. 'OK' 클릭  

  >(4) 'Database' 탭 메뉴 클릭
  >>1. '+' 버튼 클릭  
  >>1. 'Data Source' 클릭  
  >>1. 'H2' 클릭  
  >>1. 'Test Connection'이 비활성화인 상태라면, 아래 화면 쪽에 있는 H2 드라이버 다운로드 메세지 클릭
  >>1. 'URL' 항목 옆에 셀렉트 박스에서 'In-memory' 선택
  >>1. 'OK' 클릭
  
  >(5) 'Build' 메뉴 클릭
  >>1. 'Build Project' 클릭

  >(6) 'Run' 메뉴 클릭
  >>1. "Run 'KakaoCouponApplication'" 클릭
  
  >(7) 브라우저에서 "http://localhost:8092" 접속 (브라우저에서 접속 후 글자가 너무 작게 보이시면 화면 확대해주세요)