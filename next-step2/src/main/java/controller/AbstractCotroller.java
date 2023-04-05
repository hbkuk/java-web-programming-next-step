package controller;

import http.HttpRequest;
import http.HttpResponse;

public abstract class AbstractCotroller implements Controller {
	@Override
	public void service(HttpRequest request, HttpResponse response) {
		String method = request.getMethod();
		
		if( method.equals("POST")) {
			doPost(request, response);
		} else {
			doGet(request, response);
		}
	}
	protected void doPost(HttpRequest request, HttpResponse response) {
	}
		
	protected void doGet(HttpRequest request, HttpResponse response) {
	}
}
