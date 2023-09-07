# [🥒 OE 여행(오늘 이곳)](https://oetrip.site/)

![image](https://github.com/FIVEZO/back-end/assets/132897437/716b5a63-bfba-4bcb-8f19-ee76a0d1bcd2)

<br>

## 👥 안녕하세요 오늘 이곳 오이 여행입니다.

<p>막상 혼자 가기는 겁나는 여행지.</p>
<p>혼자 가기는 뻘쭘한 여행지, 이제는 든든한 짧은 동행으로 가볍게 같이 가요!</p>
<p>여행을 두 배로 즐기고 싶다면 이 모든 순간을 오이와 함께 해보세요.</p>

<br>

## 📅 프로젝트 기간

- 2023년 7월 28일 ~ 9월 8일 (6주)

<br>

 ## 🔎 주요 기능

✅ 원하는 위치의 여행지를 검색해 다른 사람들의 게시글을 둘러볼 수 있어요!

✅ 검색한 여행지는 지도 화면으로 위치를 한눈에 확인할 수 있어요!

✅ 맞춤형 여행 검색으로 내가 함께 하고 싶은 여행지를 찾아보세요!

✅ 그동안 작성한 글과 스크랩한 게시물을  한눈에 확인할 수 있어요!

✅ 채팅 기능을 통해 약속 잡기 및 대화가 가능 합니다.

✅ 실시간 알림을 통해 빠르게 확인이 가능합니다.

<br>

##  🏗 아키텍처 구성도 <br>

![image](https://github.com/FIVEZO/back-end/assets/132897437/aac0ec4e-538d-4363-a8c5-84e8c795818b)


### BACK-END STACK 🏗

<div align=center> 
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/SPRING BOOT-6DB33F?style=for-the-badge&logo=SPRING BOOT&logoColor=white">
<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">
<img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
<img src="https://img.shields.io/badge/Kakao Develop-FFCD00?style=for-the-badge&logo=kakao&logoColor=black">
<img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white">
<img src="https://img.shields.io/badge/WebSocket-000000?style=for-the-badge&logo=&logoColor=white"/>
<img src="https://img.shields.io/badge/Stomp-000000?style=for-the-badge&logo=&logoColor=white"/>
<img src="https://img.shields.io/badge/SMTP-000000?style=for-the-badge&logo=&logoColor=white"/>
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white">
<img src="https://img.shields.io/badge/Query_DSL-2C5BB4?style=for-the-badge&logoColor=white">
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black">
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">

</div>
<br>

## 💡 기술적 의사결정

<details>
<summary> STOMP </summary>

- 메세지 전송을 효율적을 하기 위해 탄생한 프로토콜로 기본적으로 pub/sub 구조로 되어있어 메세지를 전송하고 메세지를 받아 처리하는 부분이 확실히 정해져 있기 때문에 개발자 입장에서 명확하게 인지하고 개발할 수 있는 이점이 있어 채택

</details>

<details>
<summary> AWS EC2 </summary>

- 여러 다른 AWS 서비스와의 유기적인 연동이 가능하기 때문에 채택
  
</details>

<details>
<summary> Redis</summary>

- 임시 데이터 사용과 캐싱에 적합하여 사용자의 빈번한 엑세스가 발생하는 데이터를 Redis에 저장하여 데이터 엑세스 속도를 높이기 위해 채

</details>

<details>
<summary> QueryDSL</summary>

- 복잡한 동적 쿼리를 쉽게 다루기 위해 채택


</details>

<details>
<summary> WebSocket</summary>

- HTTP 통신으로 대화를 주고 받는 것을 고려했으나 대화를 전송할 때마다 요청이 가야만 하고 해당 페이지가 새로고침이 된 이후에야 전송된 내용을 조회할 수 있었다.

- 따라서 하나의 HTTP 접속을 통해 클라이언트와 서버의 양방향 통신 및 그로 인한 서버 부하를 줄일 수 있는 Websocket 을 사용했다. 클라이언트에서의 요청이 없더라도 통신이 가능했다.

</details>

<details>
<summary> SSE </summary>

- WebSocket 과 SSE 모두 실시간 통신이나, 알림 기능의 경우 양방향 통신은 불필요하기 때문에 단방향 통신인 SSE 를 사용

</details>

<details>
<summary> Swagger </summary>

- Front-End와 Back-End의 효율적이고 직관적인 의사소통을 위해 API 기능을 문서화하기 위하여 적용
  
</details>

<details>
<summary> Refresh Token</summary>

- 장기적으로 인증을 유지하고 accessToken을 갱신할 수 있음

</details>

<details>
<summary> 소셜 로그인 </summary>

- 카카오 소셜 로그인 기능을 추가하여 사용자가 더욱 편리하게 로그인 및 서비스를 이용할 수 있게 함
  
</details>

<details>
<summary> 이메일 인증 (SMTP)</summary>

- 인증으로 무분별한 사용자 접근 보안 강화

</details>

<details>
<summary> CI/CD</summary>

- GitHub Actions과 Docker를 이용하여 개발과 배포를 자동화 함
  
</details>

## [🗂️ API 명세서(Swagger)](http://localhost:8080/swagger-ui/index.html)


