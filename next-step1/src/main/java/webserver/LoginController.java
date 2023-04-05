package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;

public class LoginController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

	@Override
	protected void doGet(HttpRequest request, HttpResponse response) {
		User user = DataBase.findUserById( request.getParameter("userId") );
		if( user == null ) {
			log.debug( "user Not Found!!" );
			response.sendRedirect("/user/login_failed.html");
		} else if ( user.getPassword().equals( request.getParameter("password")) ) {
			log.debug( "login Success!!" );
			response.addHeader("Set-Cookie", "logined=true");
			response.sendRedirect("/index.html");
		} else {
			log.debug( "password Mismatch!!" );
			response.sendRedirect("/user/login_failed.html");
		}
	}
	
	
}
