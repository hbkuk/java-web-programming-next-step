package webserver;

public abstract class AbstractController implements Controller {
	
	@Override
	public void service(HttpRequest request, HttpResponse response) {
		String method = request.getMethod();
		
		if( method.equals("POST")) {
			doPost( request, response );
		} else if( method.equals("GET")) {
			doGet( request, response );
		}
	}
	
	protected void doPost(HttpRequest request, HttpResponse response) {
		
	}
	
	protected void doGet(HttpRequest request, HttpResponse response) {
		
	}

}
