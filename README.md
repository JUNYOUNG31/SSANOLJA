# **📹 SSANOLJA - 웹 화상 기술을 이용한 아이스브레이킹용 게임 서비스**

## 🎮 **로비 서비스 화면**

**구글 로그인**

![1 로그인](https://user-images.githubusercontent.com/87454934/231130339-ec6a3365-ef0b-4c6f-a07d-83df5e6d5b22.gif)

**로비**

![2 로비](https://user-images.githubusercontent.com/87454934/231130341-273b3a08-147f-4b87-a0cf-a7b0f3809b78.gif)



## **🕶 스파이폴 서비스 화면**

**게임설명**

![3 스파이폴설명](https://user-images.githubusercontent.com/87454934/231130342-fdd32fcf-aa53-481c-80f1-523cc9d218d7.gif)

**시작화면**

![4 시민시작화면](https://user-images.githubusercontent.com/87454934/231130345-5d6733a3-c48b-4616-b2e2-013630e7c929.gif)

**시작화면(스파이)**

![스파이화면](https://user-images.githubusercontent.com/87454934/231130322-9f2f11f1-6def-4cae-84bd-2730a89f8dab.gif)

**지목하기**

![지목하기](https://user-images.githubusercontent.com/87454934/231130330-c4c6b6b5-b0db-476a-b946-e024368a1bdf.gif)

**스파이폴**

![8 스파이폴](https://user-images.githubusercontent.com/87454934/231130349-ddbac6d9-ff1d-4bf7-96df-3033a6081a47.gif)

**승리화면 - 시민승리** 

![spy_win](https://user-images.githubusercontent.com/87454934/231130375-343361f9-f93d-4559-89be-e107601c50c2.gif)

**승리화면 - 스파이승리** 

![civil_win](https://user-images.githubusercontent.com/87454934/231130372-1daca884-ec30-44a1-aa1e-62be67f13840.gif)




## **🎨 텔레스테이션 서비스 화면**

**게임설명**

![9 텔레스테이션설명](https://user-images.githubusercontent.com/87454934/231130354-da798666-fb06-4679-89dd-0c81e78e66f7.gif)

**키워드입력**

![키워드입력](https://user-images.githubusercontent.com/87454934/231130332-4b4e6f99-4428-44a3-a78e-efbc59599a50.gif)

**그림그리기**

<img src="https://user-images.githubusercontent.com/87454934/231130382-cad391ce-aeb7-4fd2-8216-3fd415448768.gif" width="600" height="423"/>

**그림맞추기**

![그림맞추기](https://user-images.githubusercontent.com/87454934/231130385-93cfe495-c01f-4029-a8f9-4f1d1a1bf798.gif)

**투표하기**

![투표하기](https://user-images.githubusercontent.com/87454934/231130336-336a4682-6e3e-4656-9e92-03e8c462893a.gif)

**시상하기**

![베스트워스트](https://user-images.githubusercontent.com/87454934/231130311-d45218ee-7d1c-45d1-959d-fd99135e2628.gif)



## 🔲 **와이어프레임**

**피그마 링크** 

[링크](https://www.figma.com/file/QZVKoDuLr3hUQw0IOLP12c/프로토타입1?node-id=0%3A1)



**로비/ 방 생성**

![로비 방생성](https://user-images.githubusercontent.com/87454934/231130302-c8921d09-90f5-49e5-af41-a74af954b0d4.png)



**스파이폴**

![스파이폴](https://user-images.githubusercontent.com/87454934/231130318-a9dd7902-5c36-46d9-84a8-ec14df57bcee.png)

![스파이폴2](https://user-images.githubusercontent.com/87454934/231130320-f3a29191-ca00-4403-a2ec-b330bd7f1494.png)



**텔레스트레이션**

![텔레스테이션](https://user-images.githubusercontent.com/87454934/231130335-48d39183-c960-4a1a-8fa5-8c979d734e5f.png)



## 🔗 **ER Diagram**

![14 ER Diagram](https://user-images.githubusercontent.com/87454934/231130356-ed89d235-0519-4972-b3f4-80b15d040109.png)



## 📄 **기능 정의서**

![15 기능정의서1](https://user-images.githubusercontent.com/87454934/231130358-3361fbe5-5cda-44ab-9c4a-35e2e5ef6729.png)

![16 기능정의서2](https://user-images.githubusercontent.com/87454934/231130362-9ba2dd32-b807-4b78-9be1-1007973bc17e.png)

![17 기능정의서3](https://user-images.githubusercontent.com/87454934/231130366-322364ed-0b8a-4272-9752-7125a5f28562.png)


## 🔨 서비스 아키텍처

![18서비스아키텍처](https://user-images.githubusercontent.com/87454934/231130370-d7b28a84-0b8c-4be7-a67a-60ccb10475aa.png)

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

- **김범주**
    - backend
        - Spring Boot를 사용
            - 게임 준비 관련 API 구현
                - 방 만들기,방 참여하기, 게임 선택하기
            - 스파이폴 관련 API 구현 및 서비스 구축
                - 랜덤한 장소 및 직업 부여, 시작 장소 부여
        - 게임 규칙 내용을 RuleUtil 클래스로 구축
        - SSANOLJA DB 구축
            - 테이블 설계 및 컬럼 값 지정
    - Server
        - AWS EC2 환경 구축
        - OpenVidu 서버 구축 후 배포
        - Nginx 프록시 서버 구축(On-Premise)
            - FE 서버 구축
            - BE 서버 구축
                - Docker를 사용해 컨테이너 제작 후 사용

- **배소원**
    - DB
        - DB 테이블 설계
    - backend(Spring boot, JPA 사용)
        - 텔레스테이션 백엔드개발(플레이어 정보 저장, 앨범 보여주기, 투표 결과 저장 )
    - frontend
        - canvas api 구현
        - SSANOLJA 텔레스테이션 게임진행 화면 개발(키워드 작성, 그림 그리기)

- **강광은**
    - Backend
        - Spring Boot, JPA를 사용해 Telestation Backend API 구현
        - Google Login API 구현
    - Frontend
        - Vue와 OAuth2를 이용한 Google Login 구현
        - Vue.js, PaperCSS, Animate.css를 사용해 Telestation 투표 결과 css 스타일링

- **박준영**
    - Frontend
        - Vue.js를 활용하여 SPA 구현
        - openvidu를 활용한 webRTC 구현
        - websocket을 통한 플레이어간 실시간 통신 구현
        - 메인 화면, 로비, 게임선택화면 구성
        - 스파이폴 게임화면(시작화면, 게임화면, 투표화면, 게임종료화면) 구성
        - 텔레스테이션 이미지 websocket 통신 구현
        - 전체적인 웹 디자인 및 styled-component와 PaperCSS를 통한 css 스타일링

- **정성우**
    - Backend
        - Entity와 DB 테이블 Jpa mapping
        - SpringBoot와 Jpa를 이용한 스파이폴, 게임시작, 방만들기, 입장하기 구현
        - 사용자 닉네임 변경 구현
        - 게임규칙 정보를 enum으로 구현
    - Frontend
        - openvidu와 vue를 이용하여 webrtc 구현
        - websocket을 통한 플레이어간 데이터 교환 구현
        - 스파이폴 axios 비동기 통신 구현
        - 방번호 복사 기능, 준비 및 시작 기능 구현

- **조성현**
    - Frontend
        - Room.vue 전체적인 구조 CSS 구축
        - OpenVidu를 이용하여 방 참가 전 자신의 화면 미리보기(preview) 기능 구현
        - Frontend Home.vue 에서 구글 로그인  연동 기능 구현
        - Telestaion 전체적인 게임 구성
        - Telestation 게임 진행 시 keyword, drawing, album, BEST/Worst 로 GameMode를 정해 SPA로 라운드별 화면 구성
        - WebSocket을 통한 플레이어간 정보 공유 및 입력완료, 준비완료 상태 공유
        - Album Mode에서 자바스크립트를 이용하여 카카오톡 형식으로 앨범 나타내기 구현

        
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

