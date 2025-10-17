# The-people-of-Gwanghwamun

## Project Objective
> 광화문 지역 기반 배달 주문 관리 플랫폼을 개발하여, 주문 접수와 처리 과정을 자동화하고,
> 스프링 부트 기반 모놀리식 아키텍처 개발 경험과 직관적인 API 문서를 제공하는 것을 목표로 합니다.

## Contributors
<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="16%">
        <a href="https://github.com/taeaeaeae">
          <img src="https://avatars.githubusercontent.com/u/46617216?v=4" width="80" alt="이태경"/><br />
          <b>이태경</b>
        </a><br />
        <sub>팀장 / 주문,<br>장바구니</sub>
      </td>
      <td align="center" valign="top" width="16%">
        <a href="https://github.com/Daae-Kim">
          <img src="https://avatars.githubusercontent.com/u/68381747?v=4" width="80" alt="김다애"/><br />
          <b>김다애</b>
        </a><br />
        <sub>리뷰, 결제</sub>
      </td>
      <td align="center" valign="top" width="16%">
        <a href="https://github.com/minju26">
          <img src="https://avatars.githubusercontent.com/u/110724254?v=4" width="80" alt="김민주"/><br />
          <b>김민주</b>
        </a><br />
        <sub>회원, 인증/인가</sub>
      </td>
      <td align="center" valign="top" width="16%">
        <a href="https://github.com/S2hyeyunS2">
          <img src="https://avatars.githubusercontent.com/u/188556604?v=4" width="80" alt="김혜윤"/><br />
          <b>김혜윤</b>
        </a><br />
        <sub>메뉴, 옵션</sub>
      </td>
      <td align="center" valign="top" width="16%">
        <a href="https://github.com/seolbin01">
          <img src="https://avatars.githubusercontent.com/u/106576062?v=4" width="80" alt="박설빈"/><br />
          <b>박설빈</b>
        </a><br />
        <sub>파일, AI</sub>
      </td>
      <td align="center" valign="top" width="16%">
        <a href="https://github.com/cicle00">
          <img src="https://avatars.githubusercontent.com/u/169460927?v=4" width="80" alt="한성연"/><br />
          <b>한성연</b>
        </a><br />
        <sub>가게, 배달 지역</sub>
      </td>
    </tr>
  </tbody>
</table>
<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->

## Deploy Link
> Backend | _[https://the-people-of-gwangh.kro.kr](https://the-people-of-gwangh.kro.kr/)_
>
> API Specs | _[Swagger](https://the-people-of-gwangh.kro.kr/)_
>

## Project Architecture
### Tech Stack

#### Backend
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=postgresql&logoColor=white)

#### Infrastructure / DevOps
![AWS](https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=amazonaws&logoColor=white)
![AWS RDS](https://img.shields.io/badge/AWS_RDS-232F3E?style=flat-square&logo=amazonrds&logoColor=white)
![AWS S3](https://img.shields.io/badge/AWS_S3-232F3E?style=flat-square&logo=amazons3&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat-square&logo=githubactions&logoColor=white)

#### AI / External API Integration
![Google Gemini](https://img.shields.io/badge/Google_Gemini_API-8E75B2?style=flat-square&logo=googlegemini&logoColor=white)


### ERD
![ERD](images/ERD.png)

### System Architecture
![Architecture](images/architecture.png)

### Directory Structure
```text
src
└─main
  └─java
    └─com.caffeine.gwanghwamun
      ├─common
      │  ├─aws.s3
      │  ├─config
      │  ├─exception
      │  ├─jwt
      │  ├─redis
      │  ├─response
      │  ├─security
      │  └─success
      ├─domain
      │  ├─address
      │  │  ├─controller
      │  │  ├─dto
      │  │  │  ├─request
      │  │  │  └─response
      │  │  ├─entity
      │  │  ├─repository
      │  │  └─service
      │  ├─ai
      │  ├─cart
      │  ├─file
      │  ├─menu
      │  ├─order
      │  ├─payment
      │  ├─region
      │  ├─review
      │  ├─store
      │  └─user
      ├─BaseEntity.java
      └─GwanghwamunApplication.java
```

## Service Setup & Execution
### Development Environment
- Java 17
- Spring Boot 3.x

###  Execution
**1. Project Clone**
```bash
git clone https://github.com/your-org/gwanghwamun.git
cd gwanghwamun
```
**2. Environment Setup**
```bash
# 루트 디렉토리에 .env.dev 파일 생성
```
**3. Gradle Build**
```bash
./gradlew clean build
```
**4. Run Application**
```
./gradlew bootRun
```
