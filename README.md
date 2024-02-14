# 개요
배달 플랫폼 프로젝트

## 목적
배민, 쿠팡 배달 플랫폼과 같은 서비스를 제공하기 위해서 어떻게 구성이 되어야 할 지 알아보는 시간<br>
Gradle 의 멀티 모듈을 사용하여 각각의 모듈의 서비스를 분리하고 서로 필요에 따라 사용하는 것을 경험


## 프로젝트 전체 구조
- 아키텍처
  
- Gradle 멀티 모듈
  - Root [ Service ]
    - 하위 모듈 [ api ] - 일반 사용자 API
    - 하위 모듈 [ db ] - JPA, DB 접근 API
    - 하위 모듈 [ store-admin ] - 가맹점 사용자 API

---

## 사용 기술 및 환경
- Springboot 3.2.2
- Java17
- Gradle - Groovy 
- JPA
- Doker
- MySQL 8.X
---

## 코드 컨벤션 
- Naver code convetion 및 Naver check style 사용
    - https://naver.github.io/hackday-conventions-java/
    - https://bestinu.tistory.com/64
---

## CI/CD
- GitAction

## 테스트
- Jacoco
- k6
---
