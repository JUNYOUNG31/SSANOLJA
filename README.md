# 웹/모바일(웹 기술) 스켈레톤 프로젝트



## 카테고리

| Application | Domain | Language | Framework |
| ---- | ---- | ---- | ---- |
| :white_check_mark: Desktop Web | :black_square_button: AI | :white_check_mark: JavaScript | :white_check_mark: Vue.js |
| :white_check_mark: Mobile Web | :black_square_button: Big Data | :black_square_button: TypeScript | :black_square_button: React |
| :white_check_mark: Responsive Web | :black_square_button: Blockchain | :black_square_button: C/C++ | :black_square_button: Angular |
| :black_square_button: Android App | :black_square_button: IoT | :black_square_button: C# | :black_square_button: Node.js |
| :black_square_button: iOS App | :black_square_button: AR/VR/Metaverse | 🔲Python | 🔲Flask/Django |
| :black_square_button: Desktop App | :black_square_button: Game | :white_check_mark: Java | :white_check_mark: Spring/Springboot |
| | | :black_square_button: Kotlin | |



## 프로젝트 소개

* 프로젝트명: SSANOLJA



* 서비스 특징: 웹/모바일(웹 기술) 프로젝트를 위한 스켈레톤 프로젝트



* 주요 기능
  - 회원 관리
  - 화상 미팅룸
  - 그룹 채팅
  
  
  
* 주요 기술
  - WebRTC
  - WebSocket
  - JWT Authentication
  - REST API
  
  
  
* 참조 리소스
  * Vuetify: 디자인 전반 적용
  * Vue Argon Design System: 디자인 전반 적용
  * Vue Black Dashboard Pro(유료): 캘린더 컴포넌트 사용
  * AR Core: 구글에서 제공하는 AR 지원 라이브러리. 이미지 인식 및 오버레이 영상에 활용
  * Color Thief: 이미지 색상 추출 라이브러리. 커버 사진 색상 추출 및 배경 변경에 활용
  * Animation.css: CSS 애니메이션 지원 라이브러리. 메인 페이지 진입 애니메이션에 활용
  
  
  
* 배포 환경
  - URL: // 웹 서비스, 랜딩 페이지, 프로젝트 소개 등의 배포 URL 기입
  - 테스트 계정: // 로그인이 필요한 경우, 사용 가능한 테스트 계정(ID/PW) 기입



## 팀 소개
* 김범주: 팀장, 백엔드 개발
* 배소원:  백엔드 개발
* 강광은: 백엔드 개발
* 박준영: 프론트 엔드 개발
* 정성우: 프론트 엔드 개발
* 조성현: 프론트 엔드 개발



## 프로젝트 상세 설명

// 개발 환경, 기술 스택, 시스템 구성도, ERD, 기능 상세 설명 등





## 와이어프레임

(향후 완성되면 캡쳐를 딸 예정)

https://www.figma.com/file/QZVKoDuLr3hUQw0IOLP12c/%ED%94%84%EB%A1%9C%ED%86%A0%ED%83%80%EC%9E%851?node-id=0%3A1





## 협업 툴

- Git

- Jira

- Notion 

  [https://granite-guitar-399.notion.site/SSAnolja-f46693a1c6f04836b9e515b5f61b3724]

- Mattermost

- Webex



## 개발 룰

- 코드 컨벤션
  - 변수명, 메서드 이름은 airbnb-eslint를 참고하여 카멜케이스로 작성합니다.
  - 클래스 이름은 대문자로 시작합니다.
  - 메서드 이름은 소문자로 시작하고 동사로 짓습니다.



- 브랜치 전략

  - feature: 기능
  - develop: 개발
  - master: 배포
  - hotfix: 배포 중 급한 에러 수정

  

  - feature 브랜치가 완성되면 develop 브랜치로 merge pull request를 진행한다.
  - pull request시 충돌이 발생하면 충돌되는 코드를 작성한 당사자들끼리 코드를 리뷰하며 필요한 경우 모든 팀원들이 코드 리뷰해서 merge한다.
  - feature 브랜치 이름 명명 규칙
    - feature/[fe or be]/[기능 이름]
    - 예시) feature/fe/login

  

- 커밋 전략

  - 커밋 타입: 내용 간단하게 적어주기
  - 예시) FEAT: register REST API 작성 [#JIRA issue-number] 
  - JIRA issue-number는 Githook을 사용하여 자동으로 작성할 예정
  
  
  
  - FEAT: 새로운 기능을 추가할 경우
  
  - FIX: 버그를 고친 경우
  
  - STYLE: 코드 포맷 변경, 코드 변경이 없는 경우
  
  - REFACTOR: 변수명, 메서드 이름 수정 등 클린코드를 위한 코드 리팩토링
  
  - DOCS: Swagger, README등 문서를 수정하는 경우
  
  - RENAME: 파일 혹은 폴더명 수정 및 이동
  
  - REMOVE : 파일 삭제
  
    
  
  