package next.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;

public class SessionUtils {
	
	public static boolean isUserSession( HttpServletRequest req ) {
    	if( getUserSession(req) == null ) {
    		return false;
    	}
    	return true;
	}

	public static User getUserSession( HttpServletRequest req ) {
    	HttpSession session = req.getSession();
    	return (User) session.getAttribute("user");
	}
	public static boolean isSameUser( HttpServletRequest req ) {
		User user = DataBase.findUserById(req.getParameter("userId"));
		
		if( !user.equals( getUserSession(req) ) ) {
			return false;
		}
		return true;
		
	}
}
