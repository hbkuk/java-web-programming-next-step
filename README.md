#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* ServletContextListener 인터페이스를 구현하는 ContextLoaderListener 클래스를 통해, DB를 초기화한다. 초기화는 테이블, 초기 데이터가 명시되어 있는 jwp.sql 파일을 통해 진행한다.
* HttpServlet 인터페이스를 구현하는 DispatcherServlet 클래스를 통해 URL 매핑 초기화를 진행한다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 클라이언트가 웹 브라우저에서 URL을 통해 서버로 localhost:8080 요청한다.
* 사용자에 요청이 들어오면 HttpServlet 인터페이스를 구현하는 DispatcherServlet 클래스를 통해 해당 URI에 해당하는 컨트롤러를 찾아서 실행시키고, 반환되는 View와 Model을 HTML코드로 변환시켜 응답한다.
* 웹 브라우저는 응답으로 받은 HTML 코드를 분석해 추가적인 CSS 파일이나, js파일 등을 추가로 요청 후 응답을 받는다.


#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 사용자는 질문 목록에서 제목을 클릭하면 해당 요청 URL(/qna/show) GET 방식으로 요청한다. DispatcherServlet을 통해서 Cotroller를 찾으면 ShowController이고, 해당 글의 ID(questionId)를 통해서 DAO 클래스를 통해 해당 모델을 가져온다.
* 하지만, 클릭함과 동시에 해당 글이 삭제되었다면 어떻게 될까?
* 만약 시스템 환경에 따라서, 삭제버튼이 먼저 눌렸지만, select 시간이 더 빨랐다면? 작성자는 이 글을 삭제했는데 사용자에게 보여주게 된다.
