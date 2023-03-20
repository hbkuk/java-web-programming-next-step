package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class UserLoginController extends AbstractCotroller {
	
	public void doPost(HttpRequest request, HttpResponse response) {
		User user = DataBase.findUserById( request.getParameter("userId") );
		
		if( user == null ) {
			response.sendRedirect("/user/login_failed.html");
			return;
		}
		
		if( user.getPassword().equals( request.getParameter("password") ) ) {
			response.addHeader("Set-Cookie", "logined=true");
            response.sendRedirect("/index.html");
		} else {
			response.sendRedirect("/user/login_failed.html");
		}
	}
}
