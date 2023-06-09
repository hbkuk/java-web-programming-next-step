package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class UserCreateController extends AbstractCotroller {
	
	public void doPost(HttpRequest request, HttpResponse response) {
		User user = new User(
				request.getParameter("userId"),
				request.getParameter("password"),
				request.getParameter("name"),
				request.getParameter("email") );
		
		DataBase.addUser(user);
		response.sendRedirect("/index.html");
	}
}
