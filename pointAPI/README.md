# 무신 사전과제 -선착순 보상 API

## 목차
- [개발 환경](#개발환경)
- [빌드 및 실행하기](#빌드-및-실행하기)
- [기능 설명](#기능설명)
- [핵심 과제](#핵심과제)
- [개선 사항](#개선사항)
---
## 개발 환경
- 기본 환경
  - IDE: IntelliJ IDEA Ultimate
  - OS: Mac OS
  - GIT
- Server
  - Java11
  - Spring Boot 2.6.13
  - Kotlin 1.8
  - JPA 1.4.32
  - H2 1.4.197
  - gradle 7.5.1
  - Junit5

## 빌드 및 실행하기
- 사전 준 : git, Java설치
```
$ git clone https://github.com/psh07166/parksunghoo-musinsa-solution.git
$ cd parksunghoo-musinsa-solution/pointAPI
$ ./gradlew clean build
$ java -jar build/libs/pointAPI-0.0.1-SNAPSHOT.jar
```
- 접속 Base URI: `http://localhost:8080`
- 기본 API 문서 URI:  `http://localhost:8080/swagger-ui/`

## 기능설명

### DB
- 사용자 포인트, 포인트 보상, 보상, 보상 상세, 추가 보상 테이블을 가진다
- 사용자 테이블과 데이터는 존재하고 사용자 테이블의 데이터 등록시 사용자 포인트 데이터도 자동으로 등록된다고 가정한다.
- 보상, 보상 상세, 추가 보상 테이블의 데이터는 이미 존재한다고 가정한다.
- 사용자 포인트 테이블과 포인트 보상 테이블은 1:N의 관계를 가진다.
- 보상 테이블과 추가 보상 테이블은 1:N의 관계를 가진다
- 각 테이블의 등록, 수정일은 자동으로 세팅한다.

### API

1.보상 데이터 조회 API(필수구현)
- GET /v1/rewards/{rewardId}
- 요청받은 보상ID 보상 데이터를 조회한다.

2.보상 지급 API(필수구현)
- PUT /v1/rewards/points
- requestBody 값으로 사용자ID와 보상ID 입력받는다.(예:{"memberId": 1,"rewardId": "1"})
- 보상ID로 보상 상세 데이터를 조회 후 수정일을 실행날짜와 비교하여 다를시 선착순 수량을 데이터를 초기화 한다.
- 보상ID로 보상 상세 데이터를 조회 후 수정일을 실행날짜가 같고 선착순 수량이 0일시 HttpStatus.BAD_REQUEST 에러를 반환한다.
- 보상ID로 보상 상세 데이터를 조회때에는 락을 걸어 선행된 트랜젝션처리를 대기함으로서 수량에 대한 동시성 제어를 한다.
- 처리 종료전 수량을 -1 한다.
- 사용자 포인트 테이블의 연속 지급 카운트와 추가 보상 데이터를 비교하여 추가 보상데이저의 조건에 충족하면 추가 보상 한다.
- 사용자ID와 당일 날짜로 포인트 보상 데이터를 조회 후 데이터가 존재할시 HttpStatus.BAD_REQUEST 에러를 반환한다.
- 사용자 포인트 테이블의 연속 지급 카운트가 최대치 혹은 전날 포인트 보상 데이터가 없으면 연속 지급 카운트를 1로 세팅 그렇지 않을시 카운트 +1로 세팅한다
- 사용자 포인트 테이블의 보유 포인트를 갱신한다.
- 추가된 데이터의 조회가 가능한 URL을 반환한다

3.보상 조회 API(필수구현)
- POST /v1/rewards/points/list?searchDate=yyyy-MM-dd(선택:&sort=정렬할 키값,asc or desc)
- 요청값은 쿼리 파라미터 searchDate = 'yyyy-MM-dd' 형식으로 받는다.
- 기본 생성시간 오름차순으로 정렬하고 쿼리 파라미터로 정렬값과 오름 내림 차순을 선택할 수 있다.

4.보상 지급 상세 조회 API(선택구현)
- GET /v1/rewards/points/detail/{pointId}
- 요청값으로 보상 지급ID를 받는다.
- 입력받은 값으로 보상 지급 상세 데이터를 조회한다.

## 핵심과제
- 단위 테스트 (Unit Test) 코드를 작성하여 각 기능을 검증한다.
- 단위 테스트 (Integration Test) 코드를 작성하여 전체 기능을 검증한다.
- 멀티 스레드 테스팅을 통해 다수의 인스턴스에서의 동작에 문제가 없는지 확인한다.
- 수량이라는 데이터를 가지고 갱신을 하게 함으로서 비관적 락을 사용할 수 있도록 DB를 설계한다.
- 동시 수정이 가능성이 있는 데이터(보상 수령)를 조회 시 락을걸어 선행 트랜젝션 처리를 대기해 동시성 이슈를 예방한다.
- 포인트 이외의 보상이 추가될 수 있다고 가정하여 확장성을 고려한 DB설계 및 프로젝트 아키텍처를 설계한다.

## 개선사항
- 로직의 중요 처리 단위에 로그 출력 및 알람 송신(메일,슬랙등) 추가함으로써 운영이슈를 보다 신속하고 원활하게 대응할 수
  있도록 한다.
- api 문서를 보다 명확하게 정리한다.
- api 인증 수단을 구현하여 보안성을 높인다.