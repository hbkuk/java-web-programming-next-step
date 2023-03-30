#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* ServletContextListener 인터페이스를 구현하는 ContextLoaderListener 클래스를 통해, DB를 초기화한다. 초기화는 테이블, 초기 데이터가 명시되어 있는 jwp.sql 파일을 통해 진행한다.
* HttpServlet 인터페이스를 구현하는 DispatcherServlet 클래스를 통해 URL 매핑 초기화를 진행한다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 클라이언트가 웹 브라우저에서 URL을 통해 서버로 localhost:8080 요청한다.
* 사용자에 요청이 들어오면 HttpServlet 인터페이스를 구현하는 DispatcherServlet 클래스를 통해 해당 URI에 해당하는 컨트롤러를 찾아서 실행시키고, 반환되는 View와 Model을 HTML코드로 변환시켜 응답한다.
* 웹 브라우저는 응답으로 받은 HTML 코드를 분석해 추가적인 CSS 파일이나, js파일 등을 추가로 요청 후 응답을 받는다.


#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
