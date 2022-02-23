# **📹 SSANOLJA - 웹 화상 기술을 이용한 아이스브레이킹용 게임 서비스**

## 🎮 **로비 서비스 화면**

**구글 로그인**

![1.로그인](/uploads/ff7b0b324a95d86c748dc5e54c8e60a4/1.로그인.gif)

**로비**

![2.로비](/uploads/c81df0d9442047d78557638be5ca8cad/2.로비.gif)



## **🕶 스파이폴 서비스 화면**

**게임설명**

![3.스파이폴설명](/uploads/4712b1d01a5123215f81055ac799bfbe/3.스파이폴설명.gif)

**시작화면**

![4.시민시작화면](/uploads/bf71fb58208f3b67fc4e5cfb61c13af3/4.시민시작화면.gif)

**시작화면(스파이)**

![스파이화면](/uploads/8ab8c24e7e2e954439a08cdb95fb5886/스파이화면.gif)

**지목하기**

![지목하기](/uploads/fa9d73e7653fa3fec3e6ed9eb95698de/지목하기.gif)

**투표하기**

![고발하기](/uploads/23afdfd37b73ca84bb1b15e6f5037f95/고발하기.gif)

**스파이폴**

![8.스파이폴](/uploads/328596f1ae68777cb21d74b27d5c9447/8.스파이폴.gif)

**승리화면 - 시민승리** 

![spy_win](/uploads/150a26d553ad2179776185be5ced2364/spy_win.gif)

**승리화면 - 스파이승리** 

![civil_win](/uploads/7693b0fd72d87e7fe4f17c132b079769/civil_win.gif)




## **🎨 텔레스테이션 서비스 화면**

**게임설명**

![9.텔레스테이션설명](/uploads/a517c718914d22c04d5f295a2ef84be7/9.텔레스테이션설명.gif)

**키워드입력**

![키워드입력](/uploads/71ad2f6aa9e9f7659e4e032090ccff61/키워드입력.gif)

**그림그리기**

![그림그리기](/uploads/4595a89848168cdb7ba99dd4fd161312/그림그리기.gif)

**그림맞추기**

![그림맞추기](/uploads/d7bfcc3bc436de9f80f40d03c66899be/그림맞추기.gif)

**투표하기**

![투표하기](/uploads/19986d65e28454fd14dacf7b8bededa3/투표하기.gif)

**시상하기**

![베스트워스트](/uploads/df56a5f67b2f2a805a0ba51dc67e914d/베스트워스트.gif)



## 🔲 **와이어프레임**

**피그마 링크** 

[링크](https://www.figma.com/file/QZVKoDuLr3hUQw0IOLP12c/프로토타입1?node-id=0%3A1)



**로비/ 방 생성**

![로비_방생성](/uploads/504d4bbe6508ccc7d1409040e2c23900/로비_방생성.png)



**스파이폴**

![스파이폴](/uploads/da35ed2ed9766e1bee27421e6bb37a29/스파이폴.png)

![스파이폴2](/uploads/326b739b907838f0c0e262b42d7777b9/스파이폴2.png)



**텔레스트레이션**

![텔레스테이션](/uploads/fe9724bd950df550cf37c2ca2e0633e6/텔레스테이션.png)



## 🔗 **ER Diagram**

![14.ER_Diagram](/uploads/28f8d2401d0acb61d2a0a8bb2ae1e18a/14.ER_Diagram.png)



## 📄 **기능 정의서**

![15.기능정의서1](/uploads/916b8c17bed4f332c9444255bb13dff5/15.기능정의서1.png)

![16.기능정의서2](/uploads/5cb54e0e170194ec380364b6220b7ef7/16.기능정의서2.png)

![17.기능정의서3](/uploads/f7f1c3443d3cc8f93266ea50cbd4f6ce/17.기능정의서3.png)



## 🔨 서비스 아키텍처

![18서비스아키텍처](/uploads/1651038b7bff0a307b5357300df6cb64/18서비스아키텍처.png)

| PORT | 이름                                                  |
| ---- | ----------------------------------------------------- |
| 443  | HTTPS                                                 |
| 80   | HTTP - HTTPS로 리다이렉트(프론트 페이지로 리다이렉트) |
| 3302 | MySQL                                                 |
| 3000 | Vue, NginX Docker Container                           |
| 8080 | Spring boot Docker Container                          |
| 9002 | Openvidu                                              |



## ⚙ 기술 스택

⌨**Backend**

- Spring Boot 2.6.2
- Spring Data Jpa
- Java 8
- Mysql 5.7

⌨**Frontend**

- Vue.js 2
- Vuetify 2.4
- Vuex 3.4
- (PaperCSS)[https://www.getpapercss.com/]

⌨**Web RTC**

- openvidu 2.20

⌨ **배포**

- AWS EC2
- Docker
- Nginx



## 😀 **팀원 역할**

- 김범주
  - Backend
  - Server
- 배소원
  - Backend
- 강광은
  - Backend
- 박준영
  - Frontend
- 정성우
  - Backend
- 조성현
  - Frontend
  
    

## 🔧 **협업 툴**

- Git

- Jira

- [Notion](https://www.notion.so/SSAnolja-f46693a1c6f04836b9e515b5f61b3724)

- Mattermost

- Webex

- Discord

  

## 🙏 코드 컨벤션

```
- 변수명, 메서드 이름은 airbnb-eslint를 참고하여 카멜케이스로 작성합니다.
- 클래스 이름은 대문자로 시작합니다.
- 메서드 이름은 소문자로 시작하고 동사로 짓습니다.
```



## 🌿 브랜치 전략

```
- feature: 기능
- develop: 개발
- master: 배포
- README는 develop브런치에서 바로 작성한다.
- feature 브랜치가 완성되면 develop 브랜치로 merge pull request를 진행한다.
- pull request시 충돌이 발생하면 충돌되는 코드를 작성한 당사자들끼리 코드를 리뷰한다.
  필요한 경우 모든 팀원들이 코드 리뷰해서 merge한다.
- feature 브랜치 이름 명명 규칙
    - feature/[fe or be]/[기능 이름]
    - 예시) feature/fe/login
- develop 브랜치 규칙
    - develop-[back or front]
```



## ❗ 커밋 전략

```
- 커밋 타입: 내용 간단하게 적어주기
- 예시) FEAT: register REST API 작성

- FEAT: 새로운 기능을 추가
- FIX: 버그를 해결
- STYLE: 코드 포맷 변경, 코드 변경이 없는 경우
- REFACTOR: 변수명, 메서드 이름 수정 등 클린코드를 위한 코드 리팩토링
- DOCS: Swagger, README등 문서를 수정
- RENAME: 파일 혹은 폴더명 수정 및 이동
- REMOVE : 파일 삭제
- CHORE : 빌드 테스크 업데이트, 패키지 매니저 환경설정
```

