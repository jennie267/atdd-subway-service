<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-%3E%3D%205.5.0-blue">
  <img alt="node" src="https://img.shields.io/badge/node-%3E%3D%209.3.0-blue">
  <a href="https://edu.nextstep.camp/c/R89PYi5H" alt="nextstep atdd">
    <img alt="Website" src="https://img.shields.io/website?url=https%3A%2F%2Fedu.nextstep.camp%2Fc%2FR89PYi5H">
  </a>
  <img alt="GitHub" src="https://img.shields.io/github/license/next-step/atdd-subway-service">
</p>

<br>

# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

<br>

## 🚀 Getting Started

### Install
#### npm 설치
```
cd frontend
npm install
```
> `frontend` 디렉토리에서 수행해야 합니다.

### Usage
#### webpack server 구동
```
npm run dev
```
#### application 구동
```
./gradlew bootRun
```
<br>

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/next-step/atdd-subway-service/issues) 에 등록해주세요 :)

<br>

## 📝 License

This project is [MIT](https://github.com/next-step/atdd-subway-service/blob/master/LICENSE.md) licensed.


## 🚀 1단계 - 인수 테스트 기반 리팩터링
## 요구사항
- LineSectionAcceptanceTest 리팩터링
- LineService 리팩터링

## 구현목록
- [x] LineSectionAcceptanceTest 리팩터링
    - [x] 지하철 구간 관리 시나리오 추가
    - [x] AcceptanceTest 소스 정리
- [x] LineService의 비즈니스 로직을 도메인으로 옮기기
    - [x] Domain 단위 테스트 작성
    - [x] Domain으로 로직 옮기기

## 🚀 2단계 - 경로 조회 기능
## 요구사항
- 최단 경로 조회 인수 테스트 만들기
- 최단 경로 조회 기능 구현하기

## 구현목록
- [x] 최단 경로 조회 인수 테스트 추가
- [x] 최단 경로 조회 기능 추가
* Outside In
    - [x] 컨트롤러 레이어 구현
    - [x] 서비스 테스트 작성
    - [x] 서비스 레이어 구현
* Inside Out
    - [x] 도메인 테스트 작성
    - [x] 도메인 구현
    - [x] 도메인과 관계를 맺는 객체에 대해 기능 구현
- [x] 예외처리
    - [x] 출발역과 도착역이 같은 경우
    - [x] 출발역과 도착역이 연결이 되어 있지 않은 경우
    - [x] 존재하지 않는 출발역이나 도착역을 조회할 경우
    
    
## 🚀 3단계 - 인증을 통한 기능 구현
## 요구사항
- 토큰 발급 기능(로그인) 인수 테스트 만들기
- 인증 - 내 정보 조회 기능 완성하기
- 인증 - 즐겨 찾기 기능 완성하기

## 구현목록
- [x] 로그인 기능 인수테스트 추가 (AuthAcceptanceTest)
    - [x] 유효하지 않은 토큰 인수 테스트 추가
- [x] 내 정보 조회 기능 인수테스트 추가 (MemberAcceptanceTest - manageMyInfo)
- [x] 내 정보 조회 기능 구현
- [x] 즐겨찾기 인수 테스트 추가
- [x] 즐겨찾기 기능 구현

## 🚀 4단계 - 요금 조회
## 요구사항
- 경로 조회 시 거리 기준 요금 정보 포함하기
- 노선별 추가 요금 정책 추가
- 연령별 할인 정책 추가

## 구현목록
- [x] 경로 조회 시 거리 기준 요금 정보 추가하기
- [x] 노선별 추가 요금 정책 계산 기능 추가
    - [x] 기본운임(10㎞ 이내) : 기본운임 1,250원
    - [x] 10km초과∼50km까지(5km마다 100원)
    - [x] 50km초과 시 (8km마다 100원)
- [x] 연령별 할인 정책 계산 기능 추가
    - [x] 청소년 : 운임에서 350원을 공제한 금액의 20%할인 (13세 이상~19세 미만)
    - [x] 어린이 : 운임에서 350원을 공제한 금액의 50%할인 (6세 이상~ 13세 미만)
