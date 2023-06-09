# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* BufferedReader.readLine() 메서드를 통해서 라인 단위를 읽음.
* 사용자의 요청은 split() 메서드를 통해서 확인함.
* 요청 URL에 해당하는 파일(자원)을 byte 단위로 읽어서 전달함.

### 요구사항 2 - get 방식으로 회원가입
* GET 방식의 요청 URL에서 파라미터(데이터)를 추출할 수 있음.
* 요청 URL과 쿼리스트링의 분리는 indexOF(), subString 메서드를 활용해서 가능함.
* 요청 URL에 대한 처리를 한 후 변수 url에 /index.html을 대입해서 이동할 수 있게함.

### 요구사항 3 - post 방식으로 회원가입
* POST 방식에서는 GET방식과 다르게 요청 URL에서 파라미터(데이터)를 추출할 수 없음.
* 즉, HTTP 헤더가 아닌 HTTP 본문을 통해서 파라미터(데이터)를 추출함.
* HTTP 본문 데이터를 읽기 위해 본문의 길이인 Content-Length을 확인 후 Byte 단위로 읽어야함.

### 요구사항 4 - redirect 방식으로 이동
* HTTP 응답 헤더의 status code가 302라면 이는 리디렉션 응답 코드임.
* 요청한 리소스가 헤더에 지정된 URL로 일시적으로 이동되었음을 나타냄.

### 요구사항 5 - cookie
* 로그인 성공 시 HTTP 응답 헤더의 status code를 302로 설정 후 Set-Cookie를 추가해서 전달

### 요구사항 6 - stylesheet 적용
* CSS 파일의 응답 헤더의 Content-Type을 text/html로 보내면 웹 브라우저는 html문법으로 문서를 읽음.
* 이를 해결하기 위해서 응답 헤더의 text/css로 보냄. 

### heroku 서버에 배포 후
* 