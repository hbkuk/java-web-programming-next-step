package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.User;

public class UserLoginController extends AbstractCotroller {
	
	public void doPost(HttpRequest request, HttpResponse response) {
		User user = DataBase.findUserById( request.getParameter("userId") );
		
		if( user == null ) {
			response.sendRedirect("/user/login_failed.html");
			return;
		}
		
		if( user.getPassword().equals( request.getParameter("password") ) ) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
            response.sendRedirect("/index.html");
		} else {
			response.sendRedirect("/user/login_failed.html");
		}
	}
}
